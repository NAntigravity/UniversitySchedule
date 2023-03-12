package com.example.universityschedule.network.models.enumconverters

class EnumConverter {      // ДЕЛАТЬ ПРОВЕРКУ .isBlank() на корректность данных
    companion object{
        fun getTime(timeslot: Int): Pair<String, String>{
            return when (timeslot){
                0 -> Pair("8:45", "10:20")
                1 -> Pair("10:35", "12:10")
                2 -> Pair("12:25", "14:00")
                3 -> Pair("14:45", "16:20")
                4 -> Pair("16:35", "18:10")
                5 -> Pair("18:25", "20:00")
                6 -> Pair("20:15", "21:50")
                else -> Pair("", "")
            }
        }

        fun getRole(role: String?): String{
            return when(role){
                "0" -> "teacher"
                "1" -> "student"
                "2" -> "admin"
                else -> ""
            }
        }
    }
}