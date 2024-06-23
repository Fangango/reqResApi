package tests

import dataClasses.register.ErrorRegister
import dataClasses.register.ReqRegister
import dataClasses.register.ResRegister
import dataClasses.resources.ResResource
import dataClasses.resources.ResResources
import dataClasses.resources.Resource
import dataClasses.update.ReqUpdate
import dataClasses.update.ResUpdate
import dataClasses.users.ResUser
import dataClasses.users.ResUsers
import dataClasses.users.User
import helpers.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import strikt.api.expect
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.isEqualTo
import strikt.assertions.isNull
import testData.*
import java.time.Duration
import java.time.Instant


@ExtendWith(LoggingTestWatcher::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReqresTests{

    @Nested
    inner class RegisterTests {
        private val registrUrl = buildRequestURL(method = "register")

        @Test
        fun testDefineUserRegister() {
            LOGGER.info { "Register. Start test: define user register test" }
            val response = sendPostRequest(
                url = registrUrl,
                body = buildRequestBody(ReqRegister(email = "eve.holt@reqres.in", password = "pistol"))
            )
            val respCode = response.code
            val respBody = response.body.string()
            checkResponseCodeEqualTo(200, respCode)
            checkBodyIsValid(respBody, ResRegister::class.java)
            val respObject = deserializeResponseBody(respBody, ResRegister::class.java)
            expect {
                that(respObject.id).describedAs("id is not equal to 4").isEqualTo(4)
                that(respObject.token).describedAs("token id not correct").isEqualTo("QpwL5tke4Pnpja7X4")
            }
        }

        @ParameterizedTest(name = "email= {0}, password= {1}, expectation= {2}")
        @CsvSource(
            value = [
                "eve.holt@reqres.in,,Missing password",
                ",pistol,Missing email or username",
                ",,Missing email or username",
                "eve1.holt@reqres.in,pistol,Note: Only defined users succeed registration"],
            delimiter = ','
        )
        fun testRegisterErrors(email: String?, password: String?, expectation: String, testInfo: TestInfo) {
            LOGGER.info { "Register. Start test: ${testInfo.displayName}" }
            val response = sendPostRequest(
                url = registrUrl,
                body = buildRequestBody(ReqRegister(email = email, password = password))
            )
            val respCode = response.code
            val respBody = response.body.string()
            checkResponseCodeEqualTo(400, respCode)
            checkBodyIsValid(respBody, ErrorRegister::class.java)
            val respObject = deserializeResponseBody(respBody, ErrorRegister::class.java)
            expect {
                that(respObject.error).describedAs("Unexpected error message").isEqualTo(expectation)
            }
        }
    }
    @Nested
    inner class UpdateTests{
        private val name = "morpheus"
        private val job = "zion resident"
        private val data = ReqUpdate(name = name, job = job)
        private val updateUrl = buildRequestURL(method = "users", resource = "2")

        @Test
        fun testPutUpdate(){
            LOGGER.info { "Update. Start test: update with PUT method test" }
            val currentTime = Instant.now()
            val duration = Duration.ofMillis(3000)
            val response = sendPutRequest(
                url = updateUrl,
                body = buildRequestBody(data)
            )
            val respCode = response.code
            val respBody = response.body.string()
            checkResponseCodeEqualTo(200, respCode)
            checkBodyIsValid(respBody, ResUpdate::class.java)
            val respObject = deserializeResponseBody(respBody, ResUpdate::class.java)
            expect {
                that(respObject.name).describedAs("Unexpected name").isEqualTo(name)
                that(respObject.job).describedAs("Unexpected name").isEqualTo(job)
                that(Instant.parse(respObject.updatedAt)).describedAs("updatedAt is not correct")
                    .isApproximatelyEqualTo(currentTime, duration)
            }
        }

        @Test
        fun testPatchUpdate(){
            LOGGER.info { "Update. Start test: update with PATCH method test" }
            val currentTime = Instant.now()
            val duration = Duration.ofMillis(3000)
            val response = sendPatchRequest(
                url = updateUrl,
                body = buildRequestBody(data)
            )
            val respCode = response.code
            val respBody = response.body.string()
            checkResponseCodeEqualTo(200, respCode)
            checkBodyIsValid(respBody, ResUpdate::class.java)
            val respObject = deserializeResponseBody(respBody, ResUpdate::class.java)
            expect {
                that(respObject.name).describedAs("Unexpected name").isEqualTo(name)
                that(respObject.job).describedAs("Unexpected name").isEqualTo(job)
                that(Instant.parse(respObject.updatedAt)).describedAs("updatedAt is not correct")
                    .isApproximatelyEqualTo(currentTime, duration)
            }
        }
    }

    @Nested
    inner class DeleteTests{
        @ParameterizedTest(name = "user ID {0}")
        @CsvSource(
            value = [
                "2,204",
                "999,204",
                "yy,204",
                "'',204"],
            delimiter = ',')
        fun testDelete(userId: String, expectedCode: Int, testInfo: TestInfo) {
            LOGGER.info { "Delete. Start test: ${testInfo.displayName}" }
            val deleteUrl = buildRequestURL(method = "users", resource = userId)
            val response = sendDeleteRequest(deleteUrl)
            checkResponseCodeEqualTo(expectedCode, response.code)

        }
    }

    @Nested
    inner class GetUserTests{
        val userId = 3
        val email = "emma.wong@reqres.in"
        val firstName = "Emma"
        val lastName = "Wong"
        val avatar = "https://reqres.in/img/faces/3-image.jpg"
        val user = User(id = userId, email = email, firstName = firstName, lastName = lastName, avatar = avatar)

        @Test
        fun testGetUser(){
            LOGGER.info { "Get User. Start test: user exists" }
            val getUserUrl = buildRequestURL(method = "users", resource = userId.toString())
            val response = sendGetRequest(getUserUrl)
            val respCode = response.code
            val respBody = response.body.string()
            checkResponseCodeEqualTo(200, respCode)
            checkBodyIsValid(respBody, ResUser::class.java)
            val respObject = deserializeResponseBody(respBody, ResUser::class.java)
            expectThat(respObject.data).describedAs("User data is not correct").isEqualTo(user)
        }

        @Test
        fun testUserNotFound(){
            LOGGER.info { "Get User. Start test: user not found" }
            val getUserUrl = buildRequestURL(method = "users", resource = "22")
            val response = sendGetRequest(getUserUrl)
            val respCode = response.code
            val respBody = response.body.string()
            checkResponseCodeEqualTo(404, respCode)
            checkBodyIsValid(respBody, ResUser::class.java)
            val respObject = deserializeResponseBody(respBody, ResUser::class.java)
            expect {
                that(respObject.data).describedAs("Unexpected data").isNull()
                that(respObject.support).describedAs("Unexpected data").isNull()
            }
        }
    }

    @Nested
    inner class GetResourceTests{
        val resourceId = 2
        val name = "fuchsia rose"
        val year =  2001
        val color = "#C74375"
        val pantone_value = "17-2031"
        val resource = Resource(resourceId, name, year, color, pantone_value)

        @Test
        fun testGetResorce(){
            LOGGER.info { "Get Resource. Start test: resource exists" }
            val getResorceUrl = buildRequestURL(method = "unknown", resource = resourceId.toString())
            val response = sendGetRequest(getResorceUrl)
            val respCode = response.code
            val respBody = response.body.string()
            checkResponseCodeEqualTo(200, respCode)
            checkBodyIsValid(respBody, ResResource::class.java)
            val respObject = deserializeResponseBody(respBody, ResResource::class.java)
            expectThat(respObject.data).describedAs("Resource data is not correct").isEqualTo(resource)
        }

        @Test
        fun testUserNotFound(){
            LOGGER.info { "Get Resource. Start test: resource not found" }
            val getResorceUrl = buildRequestURL(method = "unknown", resource = "23")
            val response = sendGetRequest(getResorceUrl)
            val respCode = response.code
            val respBody = response.body.string()
            checkResponseCodeEqualTo(404, respCode)
            checkBodyIsValid(respBody, ResResource::class.java)
            val respObject = deserializeResponseBody(respBody, ResResource::class.java)
            expect {
                that(respObject.data).describedAs("Unexpected data").isNull()
                that(respObject.support).describedAs("Unexpected data").isNull()
            }
        }
    }

    @Nested
    inner class GetUsersTests{
        @Test
        fun testGetUsersWithoutParams(){
            LOGGER.info { "Get Users. Start test: send request with default params" }
            val getUsersUrl = buildRequestURL(method = "users")
            val response = sendGetRequest(getUsersUrl)
            val respCode = response.code
            val respBody = response.body.string()
            checkResponseCodeEqualTo(200, respCode)
            checkBodyIsValid(respBody, ResUsers::class.java)
            val respObject = deserializeResponseBody(respBody, ResUsers::class.java)
            expect {
                that(respObject.page).describedAs("Unexpected page").isEqualTo(1)
                that(respObject.perPage).describedAs("Unexpected perPage").isEqualTo(6)
                that(respObject.total).describedAs("Unexpected total").isEqualTo(12)
                that(respObject.totalPages).describedAs("Unexpected totalPages").isEqualTo(2)
            }
            respObject.data?.forEach { user ->
                expect { that(usersWithoutParams).describedAs("User with id=${user.id} not in expected list")
                    .contains(user) }
            }
        }

        @Test
        fun testGetUsersWithPageParams(){
            LOGGER.info { "Get Users. Start test: send request with page param" }
            val queryParams = mapOf<String, String>("page" to "2")
            val getUsersUrl = buildRequestURL(method = "users", queryParam = queryParams)
            val response = sendGetRequest(getUsersUrl)
            val respCode = response.code
            val respBody = response.body.string()
            checkResponseCodeEqualTo(200, respCode)
            checkBodyIsValid(respBody, ResUsers::class.java)
            val respObject = deserializeResponseBody(respBody, ResUsers::class.java)
            expect {
                that(respObject.page).describedAs("Unexpected page").isEqualTo(2)
                that(respObject.perPage).describedAs("Unexpected perPage").isEqualTo(6)
                that(respObject.total).describedAs("Unexpected total").isEqualTo(12)
                that(respObject.totalPages).describedAs("Unexpected totalPages").isEqualTo(2)
            }
            respObject.data?.forEach { user ->
                expect { that(userWithPageParam).describedAs("User with id=${user.id} not in expected list")
                    .contains(user) }
            }
        }

        @Test
        fun testGetUsersWithPerPageParams(){
            LOGGER.info { "Get Users. Start test: send request with per_page param" }
            val queryParams = mapOf<String, String>("per_page" to "3")
            val getUsersUrl = buildRequestURL(method = "users", queryParam = queryParams)
            val response = sendGetRequest(getUsersUrl)
            val respCode = response.code
            val respBody = response.body.string()
            checkResponseCodeEqualTo(200, respCode)
            checkBodyIsValid(respBody, ResUsers::class.java)
            val respObject = deserializeResponseBody(respBody, ResUsers::class.java)
            expect {
                that(respObject.page).describedAs("Unexpected page").isEqualTo(1)
                that(respObject.perPage).describedAs("Unexpected perPage").isEqualTo(3)
                that(respObject.total).describedAs("Unexpected total").isEqualTo(12)
                that(respObject.totalPages).describedAs("Unexpected totalPages").isEqualTo(4)
            }
            respObject.data?.forEach { user ->
                expect { that(usersWitPerPageParam).describedAs("User with id=${user.id} not in expected list")
                    .contains(user) }
            }
        }

        @Test
        fun testGetUsersWithAllParams(){
            LOGGER.info { "Get Users. Start test: send request with page and per_page params" }
            val queryParams = mapOf<String, String>("page" to "2", "per_page" to "4")
            val getUsersUrl = buildRequestURL(method = "users", queryParam = queryParams)
            val response = sendGetRequest(getUsersUrl)
            val respCode = response.code
            val respBody = response.body.string()
            checkResponseCodeEqualTo(200, respCode)
            checkBodyIsValid(respBody, ResUsers::class.java)
            val respObject = deserializeResponseBody(respBody, ResUsers::class.java)
            expect {
                that(respObject.page).describedAs("Unexpected page").isEqualTo(2)
                that(respObject.perPage).describedAs("Unexpected perPage").isEqualTo(4)
                that(respObject.total).describedAs("Unexpected total").isEqualTo(12)
                that(respObject.totalPages).describedAs("Unexpected totalPages").isEqualTo(3)
            }
            respObject.data?.forEach { user ->
                expect { that(usersWitAllParams).describedAs("User with id=${user.id} not in expected list")
                    .contains(user) }
            }
        }

        @Test
        fun testGetUsersWithIncorrectPageParams(){
            LOGGER.info { "Get Users. Start test: send request with incorrect page param" }
            val queryParams = mapOf<String, String>("page" to "20")
            val getUsersUrl = buildRequestURL(method = "users", queryParam = queryParams)
            val response = sendGetRequest(getUsersUrl)
            val respCode = response.code
            val respBody = response.body.string()
            checkResponseCodeEqualTo(200, respCode)
            checkBodyIsValid(respBody, ResUsers::class.java)
            val respObject = deserializeResponseBody(respBody, ResUsers::class.java)
            expect {
                that(respObject.page).describedAs("Unexpected page").isEqualTo(20)
                that(respObject.perPage).describedAs("Unexpected perPage").isEqualTo(6)
                that(respObject.total).describedAs("Unexpected total").isEqualTo(12)
                that(respObject.totalPages).describedAs("Unexpected totalPages").isEqualTo(2)
                that(respObject.data?.size).describedAs("Users list is not empty").isEqualTo(0)
            }
        }

        @Test
        fun testGetUsersWithBigPerPageParams(){
            LOGGER.info { "Get Users. Start test: send request with incorrect per_page param" }
            val queryParams = mapOf<String, String>("per_page" to "30")
            val getUsersUrl = buildRequestURL(method = "users", queryParam = queryParams)
            val response = sendGetRequest(getUsersUrl)
            val respCode = response.code
            val respBody = response.body.string()
            checkResponseCodeEqualTo(200, respCode)
            checkBodyIsValid(respBody, ResUsers::class.java)
            val respObject = deserializeResponseBody(respBody, ResUsers::class.java)
            expect {
                that(respObject.page).describedAs("Unexpected page").isEqualTo(1)
                that(respObject.perPage).describedAs("Unexpected perPage").isEqualTo(30)
                that(respObject.total).describedAs("Unexpected total").isEqualTo(12)
                that(respObject.totalPages).describedAs("Unexpected totalPages").isEqualTo(1)
            }
            respObject.data?.forEach { user ->
                expect { that(allUsers).describedAs("User with id=${user.id} not in expected list")
                    .contains(user) }
            }
        }
    }

    @Nested
    inner class GetResourcesTests{
        @Test
        fun testGetResourcesWithoutParams(){
            LOGGER.info { "Get Resources. Start test: send request with default params" }
            val getResourcessUrl = buildRequestURL(method = "unknown")
            val response = sendGetRequest(getResourcessUrl)
            val respCode = response.code
            val respBody = response.body.string()
            checkResponseCodeEqualTo(200, respCode)
            checkBodyIsValid(respBody, ResResources::class.java)
            val respObject = deserializeResponseBody(respBody, ResResources::class.java)
            expect {
                that(respObject.page).describedAs("Unexpected page").isEqualTo(1)
                that(respObject.perPage).describedAs("Unexpected perPage").isEqualTo(6)
                that(respObject.total).describedAs("Unexpected total").isEqualTo(12)
                that(respObject.totalPages).describedAs("Unexpected totalPages").isEqualTo(2)
            }
            respObject.data?.forEach { user ->
                expect { that(resourcesWithoutParams).describedAs("Resource with id=${user.id} not in expected list")
                    .contains(user) }
            }
        }

        @Test
        fun testGetResourcesWithPageParams(){
            LOGGER.info { "Get Resources. Start test: send request with page param" }
            val queryParams = mapOf<String, String>("page" to "2")
            val getResourcessUrl = buildRequestURL(method = "unknown", queryParam = queryParams)
            val response = sendGetRequest(getResourcessUrl)
            val respCode = response.code
            val respBody = response.body.string()
            checkResponseCodeEqualTo(200, respCode)
            checkBodyIsValid(respBody, ResResources::class.java)
            val respObject = deserializeResponseBody(respBody, ResResources::class.java)
            expect {
                that(respObject.page).describedAs("Unexpected page").isEqualTo(2)
                that(respObject.perPage).describedAs("Unexpected perPage").isEqualTo(6)
                that(respObject.total).describedAs("Unexpected total").isEqualTo(12)
                that(respObject.totalPages).describedAs("Unexpected totalPages").isEqualTo(2)
            }
            respObject.data?.forEach { user ->
                expect { that(resourcesWithPageParam).describedAs("Resource with id=${user.id} not in expected list")
                    .contains(user) }
            }
        }

        @Test
        fun testGetResourcesWithPerPageParams(){
            LOGGER.info { "Get Resources. Start test: send request with per_page param" }
            val queryParams = mapOf<String, String>("per_page" to "3")
            val getResourcessUrl = buildRequestURL(method = "unknown", queryParam = queryParams)
            val response = sendGetRequest(getResourcessUrl)
            val respCode = response.code
            val respBody = response.body.string()
            checkResponseCodeEqualTo(200, respCode)
            checkBodyIsValid(respBody, ResResources::class.java)
            val respObject = deserializeResponseBody(respBody, ResResources::class.java)
            expect {
                that(respObject.page).describedAs("Unexpected page").isEqualTo(1)
                that(respObject.perPage).describedAs("Unexpected perPage").isEqualTo(3)
                that(respObject.total).describedAs("Unexpected total").isEqualTo(12)
                that(respObject.totalPages).describedAs("Unexpected totalPages").isEqualTo(4)
            }
            respObject.data?.forEach { user ->
                expect { that(resourcesWitPerPageParam).describedAs("Resource with id=${user.id} not in expected list")
                    .contains(user) }
            }
        }

        @Test
        fun testGetResourcesWithAllParams(){
            LOGGER.info { "Get Resources. Start test: send request with page and per_page params" }
            val queryParams = mapOf<String, String>("page" to "2", "per_page" to "4")
            val getResourcessUrl = buildRequestURL(method = "unknown", queryParam = queryParams)
            val response = sendGetRequest(getResourcessUrl)
            val respCode = response.code
            val respBody = response.body.string()
            checkResponseCodeEqualTo(200, respCode)
            checkBodyIsValid(respBody, ResResources::class.java)
            val respObject = deserializeResponseBody(respBody, ResResources::class.java)
            expect {
                that(respObject.page).describedAs("Unexpected page").isEqualTo(2)
                that(respObject.perPage).describedAs("Unexpected perPage").isEqualTo(4)
                that(respObject.total).describedAs("Unexpected total").isEqualTo(12)
                that(respObject.totalPages).describedAs("Unexpected totalPages").isEqualTo(3)
            }
            respObject.data?.forEach { user ->
                expect { that(resourcesWitAllParams).describedAs("Resource with id=${user.id} not in expected list")
                    .contains(user) }
            }
        }

        @Test
        fun testGetResourcesWithIncorrectPageParams(){
            LOGGER.info { "Get Resources. Start test: send request with incorrect page param" }
            val queryParams = mapOf<String, String>("page" to "20")
            val getResourcessUrl = buildRequestURL(method = "unknown", queryParam = queryParams)
            val response = sendGetRequest(getResourcessUrl)
            val respCode = response.code
            val respBody = response.body.string()
            checkResponseCodeEqualTo(200, respCode)
            checkBodyIsValid(respBody, ResResources::class.java)
            val respObject = deserializeResponseBody(respBody, ResResources::class.java)
            expect {
                that(respObject.page).describedAs("Unexpected page").isEqualTo(20)
                that(respObject.perPage).describedAs("Unexpected perPage").isEqualTo(6)
                that(respObject.total).describedAs("Unexpected total").isEqualTo(12)
                that(respObject.totalPages).describedAs("Unexpected totalPages").isEqualTo(2)
                that(respObject.data?.size).describedAs("Resources list is not empty").isEqualTo(0)
            }
        }

        @Test
        fun testGetResourcesWithBigPerPageParams(){
            LOGGER.info { "Get Resources. Start test: send request with incorrect per_page param" }
            val queryParams = mapOf<String, String>("per_page" to "30")
            val getResourcessUrl = buildRequestURL(method = "unknown", queryParam = queryParams)
            val response = sendGetRequest(getResourcessUrl)
            val respCode = response.code
            val respBody = response.body.string()
            checkResponseCodeEqualTo(200, respCode)
            checkBodyIsValid(respBody, ResResources::class.java)
            val respObject = deserializeResponseBody(respBody, ResResources::class.java)
            expect {
                that(respObject.page).describedAs("Unexpected page").isEqualTo(1)
                that(respObject.perPage).describedAs("Unexpected perPage").isEqualTo(30)
                that(respObject.total).describedAs("Unexpected total").isEqualTo(12)
                that(respObject.totalPages).describedAs("Unexpected totalPages").isEqualTo(1)
            }
            respObject.data?.forEach { user ->
                expect { that(allResources).describedAs("Resource with id=${user.id} not in expected list")
                    .contains(user) }
            }
        }
    }
}