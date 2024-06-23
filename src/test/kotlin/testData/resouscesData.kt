package testData

import dataClasses.resources.Resource


val resource1 = Resource(
    id= 1,
    name= "cerulean",
    year= 2000,
    color= "#98B2D1",
    pantoneValue= "15-4020")

val resource2 = Resource(
    id= 2,
    name= "fuchsia rose",
    year= 2001,
    color= "#C74375",
    pantoneValue= "17-2031")

val resource3 = Resource(
    id= 3,
    name= "true red",
    year= 2002,
    color= "#BF1932",
    pantoneValue= "19-1664")

val resource4 = Resource(
    id= 4,
    name= "aqua sky",
    year= 2003,
    color= "#7BC4C4",
    pantoneValue= "14-4811")

val resource5 = Resource(
    id= 5,
    name= "tigerlily",
    year= 2004,
    color= "#E2583E",
    pantoneValue= "17-1456")

val resource6 = Resource(
    id= 6,
    name= "blue turquoise",
    year= 2005,
    color= "#53B0AE",
    pantoneValue= "15-5217")

val resource7 = Resource(
    id= 7,
    name= "sand dollar",
    year= 2006,
    color= "#DECDBE",
    pantoneValue= "13-1106")

val resource8 = Resource(
    id= 8,
    name= "chili pepper",
    year= 2007,
    color= "#9B1B30",
    pantoneValue= "19-1557")

val resource9 = Resource(
    id= 9,
    name= "blue iris",
    year= 2008,
    color= "#5A5B9F",
    pantoneValue= "18-3943")

val resource10 = Resource(
    id= 10,
    name= "mimosa",
    year= 2009,
    color= "#F0C05A",
    pantoneValue= "14-0848")

val resource11 = Resource(
    id= 11,
    name= "turquoise",
    year= 2010,
    color= "#45B5AA",
    pantoneValue= "15-5519")

val resource12 = Resource(
    id= 12,
    name= "honeysuckle",
    year= 2011,
    color= "#D94F70",
    pantoneValue= "18-2120")

val resourcesWithoutParams = listOf<Resource>(resource1, resource2, resource3, resource4, resource5, resource6)
val resourcesWithPageParam = listOf<Resource>(resource7, resource8, resource9, resource10, resource11, resource12)
val resourcesWitPerPageParam = listOf<Resource>(resource1, resource2, resource3)
val resourcesWitAllParams = listOf<Resource>(resource5, resource6, resource7, resource8)
val allResources = listOf<Resource>(
    resource1, resource2, resource3, resource4, resource5, resource6,
    resource7, resource8, resource9, resource10, resource11, resource12
)