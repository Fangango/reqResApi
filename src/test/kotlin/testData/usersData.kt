package testData

import dataClasses.users.User

val user1 = User(
    id=  1,
    email= "george.bluth@reqres.in",
    firstName= "George",
    lastName= "Bluth",
    avatar= "https://reqres.in/img/faces/1-image.jpg")

val user2 = User(
    id= 2,
    email= "janet.weaver@reqres.in",
    firstName= "Janet",
    lastName= "Weaver",
    avatar = "https://reqres.in/img/faces/2-image.jpg")

val user3 = User(
    id= 3,
    email= "emma.wong@reqres.in",
    firstName= "Emma",
    lastName= "Wong",
    avatar= "https://reqres.in/img/faces/3-image.jpg")

val user4 = User(
    id= 4,
    email= "eve.holt@reqres.in",
    firstName= "Eve",
    lastName= "Holt",
    avatar= "https://reqres.in/img/faces/4-image.jpg")

val user5 = User(
    id= 5,
    email= "charles.morris@reqres.in",
    firstName= "Charles",
    lastName= "Morris",
    avatar= "https://reqres.in/img/faces/5-image.jpg")

val user6 = User(
    id= 6,
    email= "tracey.ramos@reqres.in",
    firstName= "Tracey",
    lastName= "Ramos",
    avatar= "https://reqres.in/img/faces/6-image.jpg")

val user7 = User(
    id= 7,
    email= "michael.lawson@reqres.in",
    firstName= "Michael",
    lastName= "Lawson",
    avatar= "https://reqres.in/img/faces/7-image.jpg")

val user8 = User(
    id= 8,
    email= "lindsay.ferguson@reqres.in",
    firstName= "Lindsay",
    lastName= "Ferguson",
    avatar= "https://reqres.in/img/faces/8-image.jpg")

val user9 = User(
    id= 9,
    email= "tobias.funke@reqres.in",
    firstName= "Tobias",
    lastName= "Funke",
    avatar= "https://reqres.in/img/faces/9-image.jpg")

val user10 = User(
    id= 10,
    email= "byron.fields@reqres.in",
    firstName= "Byron",
    lastName= "Fields",
    avatar= "https://reqres.in/img/faces/10-image.jpg")

val user11 = User(
    id= 11,
    email= "george.edwards@reqres.in",
    firstName= "George",
    lastName= "Edwards",
    avatar= "https://reqres.in/img/faces/11-image.jpg")

val user12 =User(
    id= 12,
    email= "rachel.howell@reqres.in",
    firstName= "Rachel",
    lastName= "Howell",
    avatar= "https://reqres.in/img/faces/12-image.jpg")

val usersWithoutParams = listOf<User>(user1, user2, user3, user4, user5, user6)
val userWithPageParam = listOf<User>(user7, user8, user9, user10, user11, user12)
val usersWitPerPageParam = listOf<User>(user1, user2, user3)
val usersWitAllParams = listOf<User>(user5, user6, user7, user8)
val allUsers = listOf<User>(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10, user11, user12)