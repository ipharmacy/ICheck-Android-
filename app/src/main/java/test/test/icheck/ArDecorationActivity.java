package test.test.icheck;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class ArDecorationActivity extends AppCompatActivity {
    private ArFragment arFragment;
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


        });
    }

    private void addModelToScene(Anchor anchor, ModelRenderable modelRenderable) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
        transformableNode.setParent(anchorNode);
        transformableNode.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        transformableNode.select();
    }
}