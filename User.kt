package com.example.myapplication

class User {
   //members decleration
   var name : String? = null
   var email : String? = null
   var category : String? = null
   var contactno : String? = null
   var branch : String? = null
   var uid : String? = null

   constructor() {}

   constructor(name : String?,email : String?,category : String?,contactno : String?,branch : String?,uid : String?)
   {
       this.name = name
       this.email = email
       this.category = category
       this.contactno = contactno
       this.branch = branch
       this.uid = uid
   }

}