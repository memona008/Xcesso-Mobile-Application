package com.xcesso;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import static android.provider.Telephony.Mms.Part.FILENAME;

/**
 * Created by kinzaMaltta on 6/11/2018.
 */

public class SendMail extends AsyncTask<Void,Void,Void> {

    private File root;
    private ArrayList<File> fileList = new ArrayList<File>();
    //Declaring Variables
    private Context context;
    private Session session;

    //Information to send email
    private  String fileName="";
    private String email;
    private String subject;
    private String message;

    //Progressdialog to show while sending email
  //  private ProgressDialog progressDialog;
    public SendMail(Context context, String email, String subject, String message,String fileName){
        //Initializing variables
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message = message;
        this.fileName=fileName.toLowerCase();
    }
    protected void onPreExecute() {
        super.onPreExecute();
        //Showing progress dialog while sending email
      //  progressDialog = ProgressDialog.show(context,"Sending message","Please wait...",false,false);
    }
    @Override
    protected Void doInBackground(Void... voids) {
        Properties props = new Properties();

        //Configuring properties for gmail
        //If you are not using gmail you may need to change the values

        //aik dfa dobara krloon mery khyaal sy path k baad ni hua krlo
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Globals.EMAIL, Globals.PASSWORD);
                    }
                });
        try {
            //Creating MimeMessage object
            MimeMessage mm = new MimeMessage(session);

            //Setting sender address
            try {
                mm.setFrom(new InternetAddress(Globals.EMAIL));
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            //Adding receiver
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            //Adding subject
            mm.setSubject(subject);
            //Adding message
            mm.setText(message);
            MimeBodyPart messageBody = new MimeBodyPart();
            messageBody.setContent(message,"text/html");
            //Getting File
            File dir = Environment.getExternalStorageDirectory();
            String path = dir.getAbsolutePath();
            root = new File(path);
            getfile(root);
            ArrayList<String> attatchmentPaths=new ArrayList<String>();

            for (int i = 0; i < fileList.size(); i++) {
                String file_name=fileList.get(i).getName();
                System.out.println(fileList.get(i).getName());
                if(file_name.toLowerCase().contains(fileName))
                {
                    attatchmentPaths.add( fileList.get(i).getAbsolutePath()) ;
                }
            }

            Multipart mp = new MimeMultipart();
            mp.addBodyPart(messageBody);
            //mp.addBodyPart(attachment);

            if (attatchmentPaths != null && attatchmentPaths.size() > 0) {
                for (String filePath : attatchmentPaths) {
                    MimeBodyPart attachPart = new MimeBodyPart();

                    try {
                        attachPart.attachFile(filePath);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    mp.addBodyPart(attachPart);
                }
            }

            mm.setContent(mp);
            //Sending email
            Transport.send(mm);


        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Dismissing the progress dialog
     //   progressDialog.dismiss();
        //Showing a success message
        Toast.makeText(context,"Message Sent",Toast.LENGTH_LONG).show();
    }
    public ArrayList<File> getfile(File dir) {
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {

                if (listFile[i].isDirectory()) {
                    getfile(listFile[i]);

                } else {
                    if (listFile[i].getName().toLowerCase().contains(fileName))
                    {
                        fileList.add(listFile[i]);
                    }
                }

            }
        }
        return fileList;
    }

}
