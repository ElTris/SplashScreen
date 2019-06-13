package com.example.splashscreen;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class LogInViews extends AppCompatActivity {

    private EditText edtCorreo,edtpassword;
    private Button Login;
    private ImageView logos;
    String Correo, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_log_in_views);

        edtCorreo = findViewById(R.id.email);
        edtpassword = findViewById(R.id.passwords);
        Login = findViewById(R.id.btnlogin);
        logos = findViewById(R.id.imageView);
        Animation ani= AnimationUtils.loadAnimation(this,R.anim.ani);


        Correo = edtCorreo.getText().toString();
        Password = edtpassword.getText().toString();


        edtpassword.startAnimation(ani);
        edtCorreo.startAnimation(ani);
        logos.startAnimation(ani);
        Login.startAnimation(ani);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadResponse(LogInViews.this).execute(edtCorreo.getText().toString(),edtpassword.getText().toString());
            }
        });
    }


    private class DownloadResponse extends AsyncTask<String,Void,String>{
        private final String NAME_SPACE = "http://NotrhtenElectronicsWS/";
        private final String URL = "http://10.0.37.204:8080/NorthernElectronicsService/ServicesNE?WSDL";
        private final String WEBMETHOD = "VerificacionUserAdmin";
        private final String PARAMS1 = "correo";
        private final String PARAMS2 = "contrasenha";
        private final String SOAP_ACTION = NAME_SPACE + WEBMETHOD;
        public Context _context;

        public DownloadResponse(Context _context) {
            this._context = _context;
        }

        @Override
        protected String doInBackground(String... values) {
            SoapObject request = new SoapObject(NAME_SPACE, WEBMETHOD);
            //Se agrega propiedad
            request.addProperty(PARAMS1, values[0]);
            request.addProperty(PARAMS2, values[1]);

            //llamada al Servicio Web
            try {
                //se extiende de SoapEnvelope con funcionalidades de serializacion
                SoapSerializationEnvelope envelope =  new SoapSerializationEnvelope(SoapEnvelope.VER11);
                //asigna el objeto SoapObject al envelope
                envelope.setOutputSoapObject(request);
                //capa de transporte http basada en J2SE
                //crea nueva instancia -> URL: destino de datos SOAP POST
                HttpTransportSE ht = new HttpTransportSE(URL);
                //estable cabecera para la accion
                //SOAP_ACTION: accion a ejecutar
                //envelope: contiene informacion para realizar la llamada
                ht.call(SOAP_ACTION, envelope);


                //clase para encapsular datos primitivos representados por una cadena en serialización XML
                SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
                StringBuffer result = new StringBuffer(response.toString());

                Log.i("demos",result.toString());
                return result.toString();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return "Error";
            }
        }


        @Override
        protected void onPostExecute(String s) {
            if (s.trim().equalsIgnoreCase("Error")){
                Toast.makeText(getApplicationContext(),"Usuario y contraseña no Validos!",Toast.LENGTH_SHORT).show();
            }else {
                if (s == null) {
                    Toast.makeText(getApplicationContext(),"Por fabor llene los campos!",Toast.LENGTH_SHORT).show();
                }else{
                    Intent in = new Intent(_context,Menu.class);
                    in.putExtra("name",s);
                    startActivity(in);
                    finish();
                }
            }

        }
    }


}
