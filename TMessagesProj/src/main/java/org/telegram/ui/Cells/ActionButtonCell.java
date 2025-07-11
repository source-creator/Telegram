package org.telegram.ui.Cells;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.messenger.UserConfig;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.ScaleStateListAnimator;

import java.util.List;

// See: @SettingsSuggestionCell
public class ActionButtonCell extends LinearLayout {
    private Theme.ResourcesProvider resourcesProvider;

    private int currentAccount = UserConfig.selectedAccount;

    private LinearLayout linearLayout;

    public final static int TYPE_MESSAGE = 0,
            TYPE_MUTE = 1,
            TYPE_UNMUTE = 2,
            TYPE_CALL = 3,
            TYPE_VIDEO_CALL = 4,
            TYPE_SHARE = 5,
            TYPE_JOIN = 6,
            TYPE_LEAVE = 7,
            TYPE_REPORT = 8,
            TYPE_STOP = 9,
            TYPE_LIVE_STREAM = 10,
            TYPE_ADD_STORY = 11,
            TYPE_GIFT = 12,
            TYPE_GEM = 13,
            TYPE_CAMERA = 14;

    private final static int[] icons = new int[]{
            R.drawable.ic_action_message, R.drawable.ic_action_mute, R.drawable.ic_action_unmute, R.drawable.ic_action_call,
            R.drawable.ic_action_video, R.drawable.ic_action_share, R.drawable.ic_action_join, R.drawable.ic_action_leave,
            R.drawable.ic_action_report, R.drawable.ic_action_stop, R.drawable.ic_action_stream, R.drawable.ic_action_story,
            R.drawable.ic_action_gift, R.drawable.ic_action_gem, R.drawable.ic_action_camera,
    };
    private final static int [] titles = new int[]{
            R.string.Message, R.string.Mute, R.string.Unmute, R.string.Call,
            R.string.Video, R.string.Share, R.string.Join, R.string.Leave,
            R.string.Report, R.string.Stop, R.string.VoipChannelVoiceChat, R.string.AddStory,
            R.string.Gift, R.string.Gem, R.string.Camera,
    };

    List<Integer> buttons;

    public ActionButtonCell(Context context, Theme.ResourcesProvider resourcesProvider) {
        super(context);
        this.resourcesProvider = resourcesProvider;
        setOrientation(VERTICAL);

        linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(HORIZONTAL);
        addView(linearLayout, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, 60, 16, 0, 16, 0));
    }

    public void refreshButtons(Context context, List<Integer> _buttons) {
        this.buttons = _buttons;
        linearLayout.removeAllViews();

        int i = 0;
        for (final int type : buttons) {
            boolean isFirst = i++ == 0;
            boolean isLast = i == buttons.size();

            TextView textView = new TextView(context);
            textView.setBackground(Theme.AdaptiveRipple.filledRect(0xEE457CAB, 8));
            ScaleStateListAnimator.apply(textView, 0.02f, 1.5f);
            textView.setLines(1);
            textView.setSingleLine(true);
            textView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            textView.setTextColor(Theme.getColor(Theme.key_featuredStickers_buttonText, resourcesProvider));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
            textView.setTypeface(AndroidUtilities.bold());
            textView.setCompoundDrawablesWithIntrinsicBounds(0, icons[type], 0, 0); // Set image above text
            textView.setCompoundDrawablePadding(0); // Optional: Add padding between image and text
            textView.setPadding(0, 24, 0, 8);

            linearLayout.addView(textView, LayoutHelper.createLinear(0, LayoutHelper.MATCH_PARENT, 0.5f, isFirst ? 0 : 4, 0, !isLast ? 4 : 0, 0));
            textView.setOnClickListener(v -> {
                onAction(type, v);
            });

            textView.setText(LocaleController.getString(titles[type]));
        }
    }

    protected void onAction(int type, View view) {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY), heightMeasureSpec);
    }
}
