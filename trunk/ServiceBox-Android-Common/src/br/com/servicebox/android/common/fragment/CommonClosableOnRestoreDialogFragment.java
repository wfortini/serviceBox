
package br.com.servicebox.android.common.fragment;

import android.os.Bundle;

public class CommonClosableOnRestoreDialogFragment extends CommonDialogFragment {
    boolean isRestore = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isRestore = savedInstanceState != null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isRestore) {
            trackLifecycleEvent("dismiss on restore");
            dismiss();
        }
    }
}
