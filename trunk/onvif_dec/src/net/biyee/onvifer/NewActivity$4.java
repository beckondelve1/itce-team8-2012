package net.biyee.onvifer;

import android.app.ProgressDialog;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import net.biyee.android.ONVIF.RetrieveIPSecurityDeviceInfoAsync;

class NewActivity$4
  implements View.OnClickListener
{
  public void onClick(View paramView)
  {
    this.this$0.pd = new ProgressDialog(this.this$0);
    this.this$0.pd.setMessage("Please wait...");
    this.this$0.pd.setProgressStyle(0);
    this.this$0.pd.show();
    this.this$0.bitmapBackground = null;
    RetrieveIPSecurityDeviceInfoAsync localRetrieveIPSecurityDeviceInfoAsync = new RetrieveIPSecurityDeviceInfoAsync(this.this$0, this.this$0.pd, this.this$0.getApplicationContext());
    EditText localEditText = (EditText)this.this$0.findViewById(2131296298);
    String[] arrayOfString = new String[3];
    arrayOfString[0] = localEditText.getText().toString();
    arrayOfString[1] = ((EditText)this.this$0.findViewById(2131296301)).getText().toString();
    arrayOfString[2] = ((EditText)this.this$0.findViewById(2131296304)).getText().toString();
    localRetrieveIPSecurityDeviceInfoAsync.execute(arrayOfString);
  }
}

/* Location:           D:\setup\Android\Tools\classes_dex2jar.jar
 * Qualified Name:     net.biyee.onvifer.NewActivity.4
 * JD-Core Version:    0.6.0
 */