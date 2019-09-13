package com.example.ployexample;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText edtCrctext;
    TextView txtCrcvalue;
    Button btnCrc;
    static String replay;
    static StringBuilder output;
    String mAsciiValue;
    static List<String> parts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();

        btnCrc.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                String CrcInput = edtCrctext.getText().toString();

                if (!CrcInput.isEmpty()) {
                     ComposeRead(CrcInput.replace(" ","").trim());
                    //hexToASCII(CrcInput);


                } else {
                    Toast.makeText(MainActivity.this, "Please Enter crc input", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void Init() {
        edtCrctext = (EditText) findViewById(R.id.edtCrctext);
        txtCrcvalue = (TextView) findViewById(R.id.txtCrcvalue);
        btnCrc = (Button) findViewById(R.id.btnCrc);
    }


    //crc initial values1
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String ComposeRead(String input) {

        StringBuilder strBuild = new StringBuilder();
        String crcString = String.format(input);
        String CRC = CommonMethod.getCRC16CCITT(crcString, 0x1021, 0x0000, true);
        Log.e("CRC", CRC);
        txtCrcvalue.setText(CRC);
        return replay;

    }

    ///Hex to ascii value1

    public String hexToASCII(String hexValue) {
        StringBuilder sb = new StringBuilder();
        String mTrimValues = hexValue.replace(" ","").replace("00", "30").trim();
        Arrays.toString(splitToNChar(mTrimValues, 2));

        for (String component : parts) {
            //int ival = Integer.parseInt(component.replace("0x", ""), 16);
            int ival = Integer.parseInt(component, 16);
            sb.append((char) ival);
        }
        mAsciiValue = sb.toString();
        Log.e("hexToASCII", String.valueOf(mAsciiValue));
        txtCrcvalue.setText(mAsciiValue);
        //asciiToHex(mAsciiValue);
        return mAsciiValue;
    }

    ///ascii to Hex value

    public static String asciiToHex(String asciiValue)
    {
        char[] chars = asciiValue.toCharArray();
        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++)
        {
            hex.append(Integer.toHexString((int) chars[i]));
        }
        Log.e("asciiToHex", String.valueOf(hex));
        toLittleEndian(String.valueOf(hex));
        return hex.toString();
    }


    //Split a String value into 2 chare

    public static String[] splitToNChar(String text, int size) {
         parts = new ArrayList<>();

        int length = text.length();
        for (int i = 0; i < length; i += size) {
            parts.add(text.substring(i, Math.min(length, i + size)));
        }
        return parts.toArray(new String[0]);
    }

    public static int toLittleEndian(final String hex) {
        int ret = 0;
        String hexLittleEndian = "";
        if (hex.length() % 2 != 0) return ret;
        for (int i = hex.length() - 2; i >= 0; i -= 2) {
            hexLittleEndian += hex.substring(i, i + 2);
        }
        //ret = Integer.parseInt(hexLittleEndian, 16);
        Log.e("little-Endian", String.valueOf(hexLittleEndian));
        return ret;
    }

}
