package com.felipe.search;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static com.felipe.search.R.layout.*;


public class MainActivity extends AppCompatActivity {

    String[] items;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    ListView listView;
    EditText editText;

    ArrayList<String> names= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);

         listView= (ListView) findViewById(R.id.theList);
        editText= (EditText)findViewById(R.id.searchFilter);
        initList();


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              //  (MainActivity.this).adapter.getFilter().filter(charSequence);
                if(charSequence.toString().equals("")){
                    initList();
                }else{
                    searchItems(charSequence.toString());

              }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        }


        public void searchItems(String textToSearch){

        zeroList();


            for(String item:items){

                if(brainReads(item,textToSearch) ){
                   addList(item);
                  //
                    }
                else if(difference(item,textToSearch)  ) {
                   addList(item);
                }else{

                }
            }
            adapter.notifyDataSetChanged();
        }

        public void initList(){
        items= new String[]{"you","despite","pale","bake"};
        listItems= new ArrayList<String>(Arrays.asList(items));
        adapter= new ArrayAdapter<String>(this, lista_item, R.id.txtitem, listItems);
        listView.setAdapter(adapter);
        }

    public void addList(String word){
        listItems= new ArrayList<>();
        listItems.add(word);
        adapter= new ArrayAdapter<String>(this, lista_item, R.id.txtitem, listItems);
        listView.setAdapter(adapter);
    }

    public void zeroList(){
        listItems= new ArrayList<>();
        adapter= new ArrayAdapter<String>(this, lista_item, R.id.txtitem, listItems);
        listView.setAdapter(adapter);
    }

    public boolean brainReads(String x, String y) {
        char[] arrayX = x.toCharArray();
        char[] arrayY = y.toCharArray();
        int count1=0, count2=0;

        if(arrayX[0] == arrayY[0] && arrayX.length == arrayY.length ) {


            for (int i = 0; i < arrayX.length; i++) {
                if (arrayX[i] == arrayY[i]) { count1++; }else {count2++;  }
            }

            if(count2 <= (arrayX.length/3)*2 ) {
                return true;
            } else{
                return false;
            }
        }else {
            return false;
        }
    }




    public  boolean difference (String x, String y) {
        x = x.toLowerCase();
        y = y.toLowerCase();

        int [] costs = new int [y.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= x.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= y.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), x.charAt(i - 1) == y.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        if(x.equals(y)) {
            return true;
        }else  if(costs[y.length()]==1) {
            return true;
        }else{
            return false;
        }


    }

}