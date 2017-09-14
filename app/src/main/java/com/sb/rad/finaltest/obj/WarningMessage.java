
//WarningMessage.java
package com.sb.rad.finaltest.obj;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by RAD on 05-Apr-17.
 */

public class WarningMessage extends AlertDialog.Builder {

    public WarningMessage(Context context,String warningTitle,String warningMessage) {
        super(context);
        setTitle(warningTitle);
        setMessage(warningMessage);
    }
    public WarningMessage(Context context, String warningTitle, String warningMessage, int icon) {
        super(context);
        setTitle(warningTitle);
        setMessage(warningMessage);
        setIcon(icon);
    }

    public void display(){
        create();
        show();
    }

}
