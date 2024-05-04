package dataclass

data class users(
    val contribution:String="",
    val solution: ArrayList<solutions> = ArrayList<solutions>(),
    val problem: ArrayList<problems> = ArrayList<problems>(),
    val imageURL:String="",
    val email:String="",
    val code:String="",
    val name:String="",
    val ischecked:String="",
    val address:String="",
    val phone:String="",
    val type:String=""
)


