package com.example.application;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class Main_2_home extends Fragment {
    private static final String DB_FILE = "book.db",//定義資料庫檔名和資料表名稱
            DB_TABLE = "book";
    private BookeepDBOpenHelper mBookeepDBOpenHelper;
    private Button mButton10;
    private EditText mMoney_nt,
            mCaption,
            mNote;


    //<-----------------------
    private String[] type = new String[] {"收入", "支出"};
    private String[] tea = new String[]{"薪資收入","獎金收入","投資收入","兼職收入","零用金"};
    private String[][] type2 = new String[][]{{"薪資收入","獎金收入","投資收入","兼職收入","零用金"},{"飲食支出","交通支出","娛樂支出","醫療支出","生活支出","衣著支出","教育支出"}};
    private Spinner sp;//第一個下拉選單
    private Spinner sp2;//第二個下拉選單
    private Context context;
    ArrayAdapter<String> adapter ;
    ArrayAdapter<String> adapter2;
    //--------------------->

    private TextView mTxtlist;
    private Button btn;
    public Main_2_home() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_2_home, container, false);
        //從這-----------------------------------------------------------------------------------------------
        context = getContext();
        Bundle bundle = getActivity().getIntent().getExtras();


        //程式剛啟始時載入第一個下拉選單
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp = (Spinner) view.findViewById(R.id.type);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(selectListener);

        //因為下拉選單第一個為地址，所以先載入地址群組進第二個下拉選單
        adapter2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, tea);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2 = (Spinner) view.findViewById(R.id.type2);
        sp2.setAdapter(adapter2);
        //-----------------------------------------------------------------------------------------------------到這



        //啟動app時檢查是否有資料表,如果沒佑就建立一個
        mBookeepDBOpenHelper = new BookeepDBOpenHelper(getActivity().getApplicationContext(), DB_FILE, null, 1);
        //取得database物件
        SQLiteDatabase bookDB = mBookeepDBOpenHelper.getWritableDatabase();
        //檢查資料表是否存在
        Cursor cursor = bookDB.rawQuery(
                "select DISTINCT tbl_name from sqlite_master where tbl_name='" +
                        DB_TABLE + "'", null);
        //1111111------------------------------------------------------------------------------------------------------------------------------
        if (cursor != null) {
            if (cursor.getCount() == 0)//沒有資料表，需要建立 個資料表
                //利用sqlitedatabase物件操作資料庫
                bookDB.execSQL("CREATE TABLE " + DB_TABLE + "(" +
                        "_id INTEGER PRIMARY KEY," +
                        "日期 Text," +
                        "NT$ INTEGER," +
                        "摘要 Text," +
                        "狀態 Text,"+
                        "詳細狀態 Text,"+
                        "備註 Text);");
            cursor.close();
        }
        //11111111111-------------------------------------------------------------------------------------------------------------------------------
        bookDB.close();//關閉資料庫
        mButton10 = view.findViewById(R.id.button10);
        //222222222---------------------------------------------------------------------------------------------------------------------------
//        sp = view.findViewById(R.id.type);
//        sp2 = view.findViewById(R.id.type2);
        //2222222222------------------------------------------------------------------------------------------------------------------------
        mMoney_nt = view.findViewById(R.id.money_nt);
        mCaption = view.findViewById(R.id.caption);
        mNote = view.findViewById(R.id.note);

        mTxtlist = view.findViewById(R.id.txtList);
        Button btnadd = view.findViewById(R.id.btnadd);
//        Button btnlist = view.findViewById(R.id.btnlist);
        btnadd.setOnClickListener(btnAddonClick);
//        btnlist.setOnClickListener(btnListonClick);

        return view;
    }

    private final View.OnClickListener btnAddonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SQLiteDatabase bookDB = mBookeepDBOpenHelper.getWritableDatabase();
            ContentValues newRow = new ContentValues();
            newRow.put("日期", mButton10.getText().toString());
            newRow.put("NT$", mMoney_nt.getText().toString());
            newRow.put("摘要", mCaption.getText().toString());
            newRow.put("狀態", sp.getSelectedItem().toString());
            newRow.put("詳細狀態", sp2.getSelectedItem().toString());
            newRow.put("備註", mNote.getText().toString());
            bookDB.insert(DB_TABLE, null, newRow);
            bookDB.close();
            //03.27.1  利用bundle傳資料
            String dat = mButton10.getText().toString();
            int mon = Integer.parseInt(mMoney_nt.getText().toString());
            String hin = mCaption.getText().toString();
            String spi1 = sp.getSelectedItem().toString();
            String spi2 = sp2.getSelectedItem().toString();
            String not = mNote.getText().toString();
            //Intent是一种运行时绑定（run-time binding）机制，它能在程序运行过程中连接两个不同的组件。在存放资源代码的文件夹下下，
            Intent myIntent = new Intent(getActivity(), Main_3_1_2_4a_3.class);
            Bundle myBundle = new Bundle();
            myBundle.putString("dat",dat);
            myBundle.putInt("mon",mon);
            myBundle.putString("hin",hin );
            myBundle.putString("spi1",spi1 );
            myBundle.putString("spi2",spi2 );
            myBundle.putString("not", not);
            myIntent.putExtras(myBundle); //Optional parameters Activity01.this.startActivity(myIntent);
            startActivity(myIntent);//启动
            //03.27.1

        }
    };
    //44444444444-----------------------------------------------------------------------------------------------------------------
//    private final View.OnClickListener btnListonClick = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            //03.27.1
//            Intent myIntent = new Intent(getActivity(), Main_3_1_2_4a_3.class); //Intent是一种运行时绑定（run-time binding）机制，它能在程序运行过程中连接两个不同的组件。在存放资源代码的文件夹下下，
//            Bundle myBundle = new Bundle();
//            myIntent.putExtras(myBundle); //Optional parameters Activity01.this.startActivity(myIntent);
//            startActivity(myIntent);//启动
//            //03.27.1
//            SQLiteDatabase BookDB = mBookeepDBOpenHelper.getWritableDatabase();
//            Cursor c = BookDB.query(true, DB_TABLE, new String[]{"date", "money",//顯示全部資料
//                    "hint", "note"}, null, null, null, null, null, null);
//            if (c == null)
//                return;
////            if (c.getCount() == 0) {
////                mTxtlist.setText("");
////                Toast.makeText(Main_2_home.this,"沒有資料",Toast.LENGTH_LONG).show();
////            }
//            else {
//                c.moveToFirst();
//                mTxtlist.setText(c.getString(0) + c.getString(1) + c.getString(2) + c.getString(3));
//                while (c.moveToNext())
//                    mTxtlist.append("\n" + c.getString(0) + c.getString(1) +
//                            c.getString(2) + c.getString(3));
//            }
//        }
//    };
    //第一個下拉類別的監看
    private AdapterView.OnItemSelectedListener selectListener = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            //讀取第一個下拉選單是選擇第幾個
            int pos = sp.getSelectedItemPosition();
            //重新產生新的Adapter，用的是二維陣列type2[pos]
            adapter2 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, type2[pos]);
            //載入第二個下拉選單Spinner
            sp2.setAdapter(adapter2);
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}