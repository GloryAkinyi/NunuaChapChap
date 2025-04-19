package com.glory.nunuachapchap.navigation


const val ROUT_HOME = "home"
const val ROUT_ABOUT = "about"

const val ROUT_REGISTER = "Register"
const val ROUT_LOGIN = "Login"



//Products6

const val ROUT_ADD_PRODUCT = "add_product"
const val ROUT_PRODUCT_LIST = "product_list"
const val ROUT_EDIT_PRODUCT = "edit_product/{productId}"

// ✅ Helper function for navigation
fun editProductRoute(productId: Int) = "edit_product/$productId"




//Content
const val ROUT_UPLOAD_CONTENT = "upload_content"
const val ROUT_VIEW_CONTENT = "view_content"


//Task
const val ROUT_UPLOAD_TASK = "upload_task"
const val ROUT_VIEW_TASK = "view_task"


