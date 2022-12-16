package com.charging.practiceapp

import java.util.*
import android.widget.*
import android.os.Bundle
import android.view.View
import android.app.Activity
import android.view.ViewGroup
import java.text.SimpleDateFormat
import android.view.LayoutInflater
import android.app.DatePickerDialog
import androidx.fragment.app.Fragment
import kotlinx.coroutines.runBlocking
import androidx.navigation.Navigation
import com.charging.practiceapp.model.Details
import com.google.android.material.snackbar.Snackbar
import android.app.DatePickerDialog.OnDateSetListener
import com.charging.practiceapp.database.DetailsRoomDB
import com.charging.practiceapp.databinding.FragmentFormBinding

var countryCode:String = "+91"

class FormFragment : Fragment() {
    var myCalendar:Calendar= Calendar.getInstance()
    var binding: FragmentFormBinding? = null
    lateinit var myDB:DetailsRoomDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myDB = DetailsRoomDB.getDatabase(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFormBinding.inflate(layoutInflater)
        binding?.formFrag=this
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val date =
            OnDateSetListener { view, year, month, day ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                updateLabel()
            }
        binding?.etDob?.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                date,
                myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        }




        binding?.back?.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.countryCode,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding?.spinnerCountry?.adapter = adapter
        }

    }
    fun updateLabel(){
        val myFormat="MM/dd/yyyy";
        val dateFormat =SimpleDateFormat(myFormat, Locale.US);
        binding?.etDob?.setText(dateFormat.format(myCalendar.getTime()));
    }

    fun validateForm():Boolean{
        var isFormValid:Boolean=false
        var isNameValid:Boolean
        var isEmailValid:Boolean
        var isPhoneValid:Boolean
        if (binding?.etName?.text.isNullOrEmpty()){
            binding?.etName?.error = "Name cannot be empty"
            isNameValid = false
        }
        else{
            var name:String = binding?.etName?.text.toString()
            isNameValid = isOnlyLetters(name)
            if (!isNameValid){
                binding?.etName?.error = "Please enter valid name"
            }
        }

        if (binding?.etEmail?.text.isNullOrEmpty()){
            binding?.etEmail?.error = "Email cannot be empty"
            isEmailValid = false
        }
        else{
            var email:String = binding?.etEmail?.text.toString()
            isEmailValid = isEmailValid(email)
            if (!isEmailValid){
                binding?.etEmail?.error = "Please enter a valid Email"
            }
        }

        if(binding?.etMobile?.text.isNullOrEmpty()){
            binding?.etMobile?.error = "Email cannot be empty"
            isPhoneValid = false
        }
        else{
            val mobileNo:String = binding?.etMobile?.text.toString()
            isPhoneValid = isPhoneNumberValid(mobileNo)
            if (!isPhoneValid){
                binding?.etMobile?.error = "Please enter a valid number"
            }
        }
        isFormValid = isNameValid && isEmailValid && isPhoneValid
        return isFormValid
    }

    fun isOnlyLetters(name: String): Boolean {
        val regex = "^[A-Za-z ]*$".toRegex()
        return regex.matches(name)
    }

    fun isEmailValid(email:String):Boolean{
        val regex = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+$".toRegex()
        return regex.matches(email)
    }

    fun isPhoneNumberValid(mobile:String):Boolean{
        return if (mobile.length<10){
            binding?.etMobile?.error = "Please enter a valid number"
            false
        } else{
            true
        }
    }

    fun onClick(view:View){
        when(view.id){
             R.id.btnSubmit -> {
                 var isFormValid:Boolean=false
                 isFormValid = validateForm()
                 var language = ""
                 if (binding?.chkC?.isChecked == true) language += "C "
                 if (binding?.chkCplus?.isChecked == true) language += "C++ "
                 if (binding?.chkJava?.isChecked == true)  language += "Java "
                 if (binding?.chkPython?.isChecked == true)  language += "Python"
                 if (language == ""){
                     val snack = Snackbar.make(view,"Please select atleast one language",Snackbar.LENGTH_SHORT)
                     snack.show()
                     isFormValid = false
                 }
                 if (isFormValid) {
                        runBlocking {
                         if (::myDB.isInitialized) {
                             myDB.detailsDao().insert(
                                 Details(
                                     title = binding?.etName?.text.toString(),
                                     desc = binding?.etEmail?.text.toString(),
                                     mobile = binding?.etMobile?.text.toString(),
                                     languages = language
                                 )
                             )
                         }
                         Toast.makeText(requireContext(), "Data inserted", Toast.LENGTH_SHORT).show()
                     }
                     binding?.etName?.text?.clear()
                     binding?.etEmail?.text?.clear()
                     binding?.etMobile?.text?.clear()
                     binding?.chkC?.isChecked = false
                     binding?.chkCplus?.isChecked = false
                     binding?.chkJava?.isChecked = false
                     binding?.chkPython?.isChecked = false
                     Navigation.findNavController(view).navigateUp()
                 }
                 else{
                     Toast.makeText(requireContext(), "Data not inserted", Toast.LENGTH_SHORT).show()
                 }
             }
        }
    }
}

class SpinnerActivity : Activity(), AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        countryCode = parent.getItemAtPosition(pos).toString()
    }


    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }
}