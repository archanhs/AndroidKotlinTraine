package com.bcaf.training

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_calculator.*
import org.mariuszgromada.math.mxparser.Expression

class Calculator : AppCompatActivity() {
    var isOperator:Boolean = false;
    var isEqual:Boolean=false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)
    }

    fun onDigitPress(view:View){
        if (isEqual){
            txtInput.setText("");
            isEqual = false;
        }
        txtInput.append((view as Button).text);
        isOperator = false;
    }

    fun onClear(view: View){
        txtInput.setText("");
    }
    fun onOperatorPress(view: View){
        var text = txtInput.text.toString();
        if (isEqual){
            txtInput.setText("");
            isEqual = false;
        }
        if (text.last().toString()=="." || isOperator){
            var hasilText = "";
            hasilText = text.dropLast(1);
            txtInput.setText(hasilText);
            txtInput.append((view as Button).text);
            isOperator = true;
        }else{
            txtInput.append((view as Button).text);
            isOperator = true;
        }

    }
    fun onCalculate(view: View){
        var text = txtInput.text.toString();
        isEqual = true;
        if (text.last().toString()=="."){
            var text = txtInput.text.toString();
            var hasilText = "";
            hasilText = text.dropLast(1);
            txtInput.setText(hasilText);
            val e = Expression(txtInput.text.toString())
            txtInput.append("="+e.calculate().toString());
        }else{
            val e = Expression(txtInput.text.toString())
            txtInput.append("="+e.calculate().toString());
        }
    }
    fun onComa(view:View){
        var text = txtInput.text.toString();
        if (isEqual){
            txtInput.setText("");
            isEqual = false;
        }
        if (text.last().toString()!=".") {
            txtInput.append((view as Button).text);
        }
    }
}