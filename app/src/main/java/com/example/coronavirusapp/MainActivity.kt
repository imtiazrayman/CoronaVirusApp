package com.example.coronavirusapp

import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.net.URI
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
       //TODO("0 Add the permissions to access the internet     <uses-permission android:name="android.permission.INTERNET" />") //done
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO("4: Set On Click Listener for the button in code using a lambda (you can do it in XML for less credit)") // done

        button.setOnClickListener{view : View -> (view as Button)
        var i = myClass()
            i.execute() // todo 5
        }

        //TODO("5: In the onClick function start the background thread
        //TODO("6: Only if you have completed everything implement the spinner by allowing the user to select a country and displaying those countries stats using the endpoint https://corona.lmao.ninja/countries and cycling through the JSON array to find the appropriate country and displaying its data")

        mySpinner.adapter = ArrayAdapter.createFromResource(this,R.array.menu_items, R.layout.support_simple_spinner_dropdown_item)
        mySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(mySpinner.getItemAtPosition(position).toString() == "Global Stats"){
                    myClass().path = "all"
                }
                else if(mySpinner.getItemAtPosition(position).toString() == "China"){
                    myClass().path = "countries"
                    myClass().country = "China"
                }
                else if(mySpinner.getItemAtPosition(position).toString() == "Korea"){
                    myClass().path = "countries"
                    myClass().country = "S. Korea"
                }
                else if(mySpinner.getItemAtPosition(position).toString() == "USA"){
                    myClass().path = "countries"
                    myClass().country = "USA"
                }
                else if(mySpinner.getItemAtPosition(position).toString() == "Iran"){
                    myClass().path = "countries"
                    myClass().country = "Iran"
                }
            }
        }
    }

    inner class myClass : AsyncTask<Void, Void , String>(){
        var path = ""
        var country = ""

        override fun doInBackground(vararg params: Void?): String {
           var toReturn = ""
            val buildUri = Uri.parse("https://corona.lmao.ninja/all").buildUpon()
                //.appendPath(path)
                //.appendQueryParameter("country" ,"China")
                //.appendPath("countries")
                .build()

            toReturn = URL(buildUri.toString()).readText()
            return toReturn
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            var myjson = JSONObject(result)
            var i = 1

           /* if(path == "countries"){
               var array =  myjson.getJSONArray("").getJSONObject(i).getJSONObject(country)

                var cases = array.getString("cases")
                var deaths = array.getString("deaths")
                var recovered = array.getString("recovered")
                output(cases, deaths, recovered)
            }*/
            //else {

                var cases = myjson.getString("cases")
                var deaths = myjson.getString("deaths")
                var recovered = myjson.getString("recovered")


                output(cases, deaths, recovered)
           // }
        }
    }

    fun output(cases :String, deaths : String, recovered : String) { //todo 3
            textView.text = "Cases : \n$cases\nDeaths :\n$deaths\nRecovered :\n$recovered"

    }








    //TODO("1: Create an inner class that implements Async task specifying the  necessary generic parameters") //done

    //TODO("2: Implement a function to run in another thread, and within its body, make the appropriate API call to the endpoint https://corona.lmao.ninja/all, it will return json formatted as follows {"cases":109978,"deaths":3827,"recovered":62240}") // done
    //TODO("3: Implement a function  that deals with the return from the API call and displays the data") // done

}
