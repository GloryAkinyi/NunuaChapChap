package com.glory.nunuachapchap.navigation


const val ROUT_HOME = "home"
const val ROUT_ABOUT = "about"

const val ROUT_REGISTER = "Register"
const val ROUT_LOGIN = "Login"





//Products

const val ROUT_ADD_PRODUCT = "add_product"
const val ROUT_PRODUCT_LIST = "product_list"
const val ROUT_EDIT_PRODUCT = "edit_product/{productId}"

// ✅ Helper function for navigation
fun editProductRoute(productId: Int) = "edit_product/$productId"

//End


