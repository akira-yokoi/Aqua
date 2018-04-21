package org.amd.aqua;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.amd.aqua.fragment.FragmentActionListener;
import org.amd.aqua.model.SupportItem;
import org.amd.aqua.model.Task;
import org.amd.aqua.util.TaskManager;
import org.amd.aqua.util.ViewUtil;

public class SupportItemCompleteAcitivity extends AppCompatActivity implements FragmentActionListener {

    public static final String EXTRA_SUPPORT_ITEM = "SUPPORT_ITEM";

    private Button finishButton = null;

    private Button closeButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_item_complete);

        setTitle(R.string.title_finish_support);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        {
            TaskManager taskManager = TaskManager.getInstance(this);
            final SupportItem item = (SupportItem) getIntent().getSerializableExtra(EXTRA_SUPPORT_ITEM);
            Task task = taskManager.getTask(item.taskId);

            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageResource(task.iconResourceId);

            TextView taskText = (TextView) findViewById(R.id.taskText);
            taskText.setText(task.taskName);
        }

        {
            ImageButton image1Button = (ImageButton) findViewById(R.id.image1Button);
            image1Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: カメラ or ギャラリーの起動
                }
            });
        }

        {
            finishButton = (Button) findViewById(R.id.finishButton);
            finishButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewUtil.showShortToast(SupportItemCompleteAcitivity.this, R.string.message_requested);
                    finish();
                }
            });
            closeButton = (Button) findViewById(R.id.closeButton);
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

    }

    public void onAction(Fragment fragment, int actionId) {
    }

    public void onSelect(Fragment fragment, Object item) {
    }
}
