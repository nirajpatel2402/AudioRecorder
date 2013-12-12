package com.example.audiorecorder;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.android.internal.telephony.ITelephony;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MainActivity extends Activity {

	  MediaRecorder recorder;   
	  File audiofile = null;
	  private static final String TAG = "SoundRecordingActivity";
	  private View startButton;
	  private View stopButton;
	  ToggleButton bPickUp;
	  BroadcastReceiver callpicker;
	  TelephonyManager telephonyManager;
	  
	  ITelephony telephonyService;

	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    startButton = findViewById(R.id.start);
	    stopButton = findViewById(R.id.stop);
	    bPickUp = (ToggleButton) findViewById (R.id.toggleButton1);
	    
	    
/*	    bPickUp.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				
									
	//			pickupCall();
				
				

			}
	    });
	  

*/
	    
	    
	    
	    
	    
	    
	    bPickUp.setOnCheckedChangeListener(new OnCheckedChangeListener() {

	    	 @Override
	    	 public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	    	 // TODO Auto-generated method stub
	    	 callpicker =new BroadcastReceiver()
	    	 {
	    	 @Override
	    	 public void onReceive(Context context, Intent intent) {
	    	 // TODO Auto-generated method stub
	    	 telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
	    	 //Java Reflections
	    	 Class c = null;
	    	 try {
	    	 c = Class.forName(telephonyManager.getClass().getName());
	    	 } catch (ClassNotFoundException e) {
	    	 // TODO Auto-generated catch block
	    	 e.printStackTrace();
	    	 }
	    	 Method m = null;
	    	 try {
	    	 m = c.getDeclaredMethod("getITelephony");
	    	 } catch (SecurityException e) {
	    	 // TODO Auto-generated catch block
	    	 e.printStackTrace();
	    	 } catch (NoSuchMethodException e) {
	    	 // TODO Auto-generated catch block
	    	 e.printStackTrace();
	    	 }
	    	 m.setAccessible(true);
	    	 try {
	    	 telephonyService = (ITelephony)m.invoke(telephonyManager);
	    	 } catch (IllegalArgumentException e) {
	    	 // TODO Auto-generated catch block
	    	 e.printStackTrace();
	    	 } catch (IllegalAccessException e) {
	    	 // TODO Auto-generated catch block
	    	 e.printStackTrace();
	    	 } catch (InvocationTargetException e) {
	    	 // TODO Auto-generated catch block
	    	 e.printStackTrace();
	    	 }
	    	 telephonyManager.listen(callBlockListener, PhoneStateListener.LISTEN_CALL_STATE);
	    	 }//onReceive()
	    	 PhoneStateListener callBlockListener = new PhoneStateListener()
	    	 {
	    	 public void onCallStateChanged(int state, String incomingNumber)
	    	 {
	    	 if(state==TelephonyManager.CALL_STATE_RINGING)
	    	 {
	    	 if(bPickUp.isChecked()==true)
	    	 {
	    	 try {
	    	 telephonyService.endCall();
	    	 } catch (RemoteException e) {
	    	 // TODO Auto-generated catch block
	    	 e.printStackTrace();
	    	 }
	    	 }
	    	 }
	    	 }
	    	 };
	    	 };//BroadcastReceiver
	    	 IntentFilter filter= new IntentFilter("android.intent.action.PHONE_STATE");
	    	 registerReceiver(callpicker, filter);
	    	 }
	    	 });
	    	}
	    
	    
	    
	    
	    
	    

	    
	    
	    
	    
	    
	    

	  
	    
	    
	    private void pickupCall() {
				// TODO Auto-generated method stub
				

				// TODO Auto-generated method stub

				callpicker = new BroadcastReceiver() {
					@Override
					public void onReceive(Context context, Intent intent) {
						// TODO Auto-generated method stub

						telephonyManager = (TelephonyManager) context
								.getSystemService(Context.TELEPHONY_SERVICE);
						// Java Reflections
						Class c = null;
						try {
							c = Class.forName(telephonyManager.getClass().getName());
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Method m = null;
						try {
							m = c.getDeclaredMethod("getITelephony");
						} catch (SecurityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						m.setAccessible(true);
						try {
							telephonyService = (ITelephony) m.invoke(telephonyManager);
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						telephonyManager.listen(callBlockListener,
								PhoneStateListener.LISTEN_CALL_STATE);

					}// onReceive()

					PhoneStateListener callBlockListener = new PhoneStateListener() {
						public void onCallStateChanged(int state, String incomingNumber) {
							if (state == TelephonyManager.CALL_STATE_RINGING) {
															
								
								if (bPickUp.isChecked()==true) {
									
									try {
										telephonyService.endCall();
							//			telephonyService.answerRingingCall();
									} catch (RemoteException e) {
										// TODO Auto-generated catch block
										e.printStackTrace(); 
									}

								}
							
							}

						}

						

					};

				};// BroadcastReceiver

				IntentFilter filter = new IntentFilter(
						"android.intent.action.PHONE_STATE");
				registerReceiver(callpicker, filter);
	    
	  }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    

	  public void startRecording(View view) throws IOException {

	    startButton.setEnabled(false);
	    stopButton.setEnabled(true);

	    File sampleDir = Environment.getExternalStorageDirectory();
	    try {
	      audiofile = File.createTempFile("sound", ".mp3", sampleDir);
	    } catch (IOException e) {
	      Log.e(TAG, "sdcard access error");
	      return;
	    }
	    
	    
	    recorder = new MediaRecorder();
	    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
	    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
	    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
	    recorder.setOutputFile(audiofile.getAbsolutePath());
	    recorder.prepare();
	    recorder.start();
	  }
	  
	  

	  public void stopRecording(View view) {
	    startButton.setEnabled(true);
	    stopButton.setEnabled(false);
	    recorder.stop();
	    recorder.release();
	    addRecordingToMediaLibrary();
	  }

	  protected void addRecordingToMediaLibrary() {
	    ContentValues values = new ContentValues(4);
	    long current = System.currentTimeMillis();
	    values.put(MediaStore.Audio.Media.TITLE, "audio" + audiofile.getName());
	    values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
	    values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
	    values.put(MediaStore.Audio.Media.DATA, audiofile.getAbsolutePath());
	    ContentResolver contentResolver = getContentResolver();

	    Uri base = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	    Uri newUri = contentResolver.insert(base, values);

	    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
//	    Toast.makeText(this, "Added File " + newUri, Toast.LENGTH_LONG).show();
//	    Toast.makeText(this, "File has been added", Toast.LENGTH_LONG).show();
	  }
	  
	  
	  
	  
	  
	    
	  @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		 if (callpicker != null)
		 {
		 unregisterReceiver(callpicker);
		 callpicker = null;
		 }
	}

	    }