package test.test.icheck;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import test.test.icheck.RetroFit.IMyService;
import test.test.icheck.RetroFit.RetrofitClient;
import test.test.icheck.adapter.MessageAdapter;
import test.test.icheck.entity.MessageChat;
import test.test.icheck.entity.MessageToDecode;
import test.test.icheck.entity.Product;
import test.test.icheck.entity.reviews;

public class ChatActivity extends AppCompatActivity implements TextWatcher {
    private String name;
    private WebSocket webSocket;
    private String SERVER_PATH = "https://tranquil-journey-23890.herokuapp.com";
    private EditText messageEdit;
    private View sendBtn, pickImgBtn;
    private RecyclerView recyclerView;
    private int IMAGE_REQUEST_ID = 1;
    private MessageAdapter messageAdapter;
    IMyService iMyService;
    private SharedPreferences sp,sharedp ;
    String connectedUserId;
    String targetUser;
    String imageUser;
    ArrayList<MessageChat> messageChats = new ArrayList<>();
    public static final String FILE_NAME = "test.test.icheck.shared";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initiateSocketConnection();
        sp = getApplicationContext().getSharedPreferences(ChatActivity.FILE_NAME,MODE_PRIVATE);
        connectedUserId = sp.getString("userId","");
        Bundle extras = getIntent().getExtras();
        targetUser= extras.getString("senderId");
        imageUser= extras.getString("imageSender");
    }
    private void initiateSocketConnection() {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(SERVER_PATH).build();
        webSocket = client.newWebSocket(request, new SocketListener());

    }



    private void resetMessageEdit() {

        messageEdit.removeTextChangedListener( this);

        messageEdit.setText("");
        sendBtn.setVisibility(View.INVISIBLE);
        pickImgBtn.setVisibility(View.VISIBLE);

        messageEdit.addTextChangedListener((TextWatcher) this);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String string = s.toString().trim();

        if (string.isEmpty()) {
            resetMessageEdit();
        } else {

            sendBtn.setVisibility(View.VISIBLE);
            pickImgBtn.setVisibility(View.INVISIBLE);
        }
    }

    private class SocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);

            runOnUiThread(() -> {
                Toast.makeText(ChatActivity.this,
                        "Socket Connection Successful!",
                        Toast.LENGTH_SHORT).show();

                initializeView();
            });

        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);

            runOnUiThread(() -> {

                try {

                    JSONObject jsonObject = new JSONObject(text);
                    //jsonObject.put("isSent", false);
                    jsonObject.put("senderId",targetUser);
                    jsonObject.put("receiverId", connectedUserId);
                    System.out.println("text : "+text);
                    JSONObject obj = new JSONObject(text);
                    Gson g = new Gson();
                    MessageToDecode s = g.fromJson(text, MessageToDecode.class);
                    System.out.println("DECODE MESSAGE : "+s.getMessage());
                    jsonObject.put("type","text");
                    jsonObject.put("message",s.getMessage());
                    //jsonObject.put("senderId", true);
                    messageAdapter.addItem(jsonObject);
                    recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            });

        }
    }


    private void initializeView() {
        messageEdit = findViewById(R.id.chatMessage);
        messageEdit.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        sendBtn = findViewById(R.id.btn_sendChat);
       pickImgBtn = findViewById(R.id.pickImage);
        recyclerView = findViewById(R.id.chatRv);
        messageAdapter = new MessageAdapter(getLayoutInflater(),connectedUserId,messageChats,imageUser,getApplicationContext());
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        getMessage();

        messageEdit.addTextChangedListener(this);
        sendBtn.setOnClickListener(v -> {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("senderId", connectedUserId);
                jsonObject.put("receiverId",targetUser);
                jsonObject.put("type","text");
                jsonObject.put("message", messageEdit.getText().toString());
                webSocket.send(jsonObject.toString());
               // jsonObject.put("isSent", true);
                sendMessageToBase();
                messageAdapter.addItem(jsonObject);
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                resetMessageEdit();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        });
        pickImgBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Pick image"),
                    IMAGE_REQUEST_ID);
        });


    }

    private void sendMessageToBase() {
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);
        HashMap<String,String> map = new HashMap<>();
        map.put("sender",connectedUserId);
        map.put("receiver",targetUser);
        map.put("type","text");
        map.put("message",messageEdit.getText().toString());
        Call <HashMap<String,String>> call = iMyService.addMessage(map);
        call.enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call, retrofit2.Response<HashMap<String, String>> response) {
                System.out.println("Message send To Base ");
                Toast.makeText(getApplicationContext(),"Message send succs",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                System.out.println("Failed ");
            }
        });
    }
    private void getMessage() {
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);
        HashMap<String,String> map = new HashMap<>();
        map.put("senderId",targetUser);
        map.put("connectedId",connectedUserId);
        Call<ArrayList<MessageChat>> call = iMyService.getMessages(map);
        call.enqueue(new Callback<ArrayList<MessageChat>>() {
            @Override
            public void onResponse(Call<ArrayList<MessageChat>> call, retrofit2.Response<ArrayList<MessageChat>> response) {
                ArrayList<MessageChat> messageReceived = response.body();

                for (int i =0;i<messageReceived.size();i++){
                    if (connectedUserId.equals(messageReceived.get(i).getSender())){
                        MessageChat msg = new MessageChat(connectedUserId,"text",messageReceived.get(i).getMessage(),messageReceived.get(i).getCreatedAt());
                        messageChats.add(msg);
                    }else{
                        MessageChat msg = new MessageChat(messageReceived.get(i).getSender(),"text",messageReceived.get(i).getMessage(),messageReceived.get(i).getCreatedAt());
                        messageChats.add(msg);
                    }
                }
                Collections.sort(messageChats);
          /*      System.out.println("MESSAGE SORTED O JAWOU BEHI : ");
                for(int i=0;i<messageChats.size();i++){
                    System.out.println("date : "+messageChats.get(i).getCreatedAt()+" message :  "+messageChats.get(i).getMessage());
                }
            */
                for(int i=0;i<messageChats.size();i++){
                    JSONObject jsonObjectSend = new JSONObject();

                    try {
                        jsonObjectSend.put("senderId",messageChats.get(i).getSender());
                        jsonObjectSend.put("receiverId", targetUser);
                        jsonObjectSend.put("type","text");
                        jsonObjectSend.put("message", messageChats.get(i).getMessage());
                        //  webSocket.send(jsonObjectSend.toString());
                        // jsonObject.put("isSent", true);
                       // sendMessageToBase();


                       // resetMessageEdit();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Message Chats : "+messageChats);
                    messageAdapter.addItem(jsonObjectSend);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<MessageChat>> call, Throwable t) {
                System.out.println("Failed msg recieve ");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST_ID && resultCode == RESULT_OK) {
            try {
                InputStream is = getContentResolver().openInputStream(data.getData());
                Bitmap image = BitmapFactory.decodeStream(is);
                sendImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    private void sendImage(Bitmap image) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
        String base64String = Base64.encodeToString(outputStream.toByteArray(),
                Base64.DEFAULT);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("image", base64String);
            webSocket.send(jsonObject.toString());
            jsonObject.put("isSent", true);
            messageAdapter.addItem(jsonObject);
            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}