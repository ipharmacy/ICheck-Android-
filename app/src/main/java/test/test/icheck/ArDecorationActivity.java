package test.test.icheck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.media.CamcorderProfile;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class ArDecorationActivity extends AppCompatActivity {
    private ArFragment arFragment;
    ArRecordVideo videoRecorder;
    private static final int PERMISSION_REQUEST=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar_decoration);

        Bundle extras = getIntent().getExtras();
        String name = extras.getString("name")+".sfb";
        System.out.println("NAME IN AR : "+name);


        arFragment= (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ArFragment);
        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            Anchor  anchor = hitResult.createAnchor();
            ModelRenderable.builder()
                    .setSource(this, Uri.parse(name))
                    .build()
                    .thenAccept(modelRenderable -> addModelToScene(anchor,modelRenderable))
                    .exceptionally(throwable -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage(throwable.getMessage())
                                .show();
                        return null;

                    });
           /* Button btnRecord = findViewById(R.id.RecordAr);
            btnRecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(videoRecorder == null ){
                        videoRecorder = new ArRecordVideo();
                        videoRecorder.setSceneView(arFragment.getArSceneView());
                        int orientation = getResources().getConfiguration().orientation;
                        videoRecorder.setVideoQuality(CamcorderProfile.QUALITY_HIGH,orientation);
                    }
                   boolean isRecording =  videoRecorder.onToggleRecord();
                    if (!isRecording){
                        Toast.makeText(ArDecorationActivity.this,"Started Recording",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ArDecorationActivity.this,"Recording stopped",Toast.LENGTH_SHORT).show();
                    }
                }
            });*/

        });

    }
   /* @Override
    public void onResume() {
        super.onResume();
        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST);
        }
    }*/
    private void addModelToScene(Anchor anchor, ModelRenderable modelRenderable) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
        transformableNode.setParent(anchorNode);
        transformableNode.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        transformableNode.select();
    }
}