package net.biyee.onvifer;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ImageButton;
import net.biyee.android.ONVIF.Profiles;
import net.biyee.android.ONVIF.utilityONVIF;
import net.biyee.android.utility;

class NewActivity$6 extends AsyncTask<Void, Void, Void>
{
  protected Void doInBackground(Void[] paramArrayOfVoid)
  {
    this.this$0.bitmapBackground = utilityONVIF.RetrieveSnapshot(this.val$iPictureSizeDesired, this.this$0.od, this.val$pOptimal, this.this$0.pd);
    return null;
  }

  protected void onPostExecute(Void paramVoid)
  {
    if (this.this$0.bitmapBackground != null)
      NewActivity.access$0(this.this$0);
    ImageButton localImageButton = (ImageButton)this.this$0.findViewById(2131296307);
    localImageButton.setVisibility(0);
    localImageButton.setFocusable(true);
    localImageButton.setFocusableInTouchMode(true);
    localImageButton.requestFocus();
    utility.showConfirmationDialog(this.this$0, "Save now?  You can save it later by clicking the save button", new NewActivity.6.1(this));
    this.this$0.pd.dismiss();
    super.onPostExecute(paramVoid);
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.NewActivity.6
 * JD-Core Version:    0.6.0
 */