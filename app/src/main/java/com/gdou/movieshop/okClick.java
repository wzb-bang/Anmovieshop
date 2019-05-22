package com.gdou.movieshop;

import android.content.DialogInterface;

class okClick implements DialogInterface.OnClickListener {
    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }
}
