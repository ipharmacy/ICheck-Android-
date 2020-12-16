package test.test.icheck;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.wearable.Asset;
import com.google.ar.core.AugmentedFace;
import com.google.ar.core.Frame;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.Texture;
import com.google.ar.sceneform.ux.AugmentedFaceNode;

import java.util.Collection;

public class ArCustomFaceFiltreActivity extends AppCompatActivity {
    private ModelRenderable modelRenderable;
    private Texture texture;
    private boolean isAdde = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar_custom_face_filtre);
        Bundle extras = getIntent().getExtras();
        String name = extras.getString("name")+".sfb";
        if (name.equals("null.sfb")){
            name ="Sunglasses.sfb";
        }
        String nameFiltre = extras.getString("nameFiltre")+".png";
        if (nameFiltre.equals("null.png")){
            nameFiltre="blankpagear.png";
        }
        ArCustomFaceFiltre arCustomFaceFiltre = (ArCustomFaceFiltre) getSupportFragmentManager().findFragmentById(R.id.IdArFaceFiltre);
        ModelRenderable.builder()

               // .setSource(this,R.raw.)
                .setSource(this,Uri.parse(name))
                .build()
                .thenAccept(renderable -> {
                    modelRenderable = renderable;
                    modelRenderable.setShadowCaster(false);
                    modelRenderable.setShadowReceiver(false);

                });
        Texture.builder()
                .setSource(this,Uri.parse(nameFiltre))
                .build()
                .thenAccept(texture ->this.texture = texture);
        arCustomFaceFiltre.getArSceneView().setCameraStreamRenderPriority(Renderable.RENDER_PRIORITY_FIRST);
        arCustomFaceFiltre.getArSceneView().getScene().addOnUpdateListener(frameTime -> {
            if (modelRenderable == null || texture == null )
                return;
            Frame frame = arCustomFaceFiltre.getArSceneView().getArFrame();
            Collection<AugmentedFace> augmentedFaces = frame.getUpdatedTrackables(AugmentedFace.class);
            for (AugmentedFace augmentedFace : augmentedFaces){
                if(isAdde)
                    return;
                AugmentedFaceNode augmentedFaceNode = new AugmentedFaceNode(augmentedFace);
                augmentedFaceNode.setParent(arCustomFaceFiltre.getArSceneView().getScene());
                System.out.println("modelRenderable : "+modelRenderable);
                augmentedFaceNode.setFaceRegionsRenderable(modelRenderable);
                augmentedFaceNode.setFaceMeshTexture(texture);
                isAdde = true;
            }
        });
    }
}