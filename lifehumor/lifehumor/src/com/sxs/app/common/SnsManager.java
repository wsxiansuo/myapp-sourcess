package com.sxs.app.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sxs.app.lifehumor.R;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.CircleShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.WeiXinShareContent;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.sso.UMWXHandler;

/**
 /**
 * ˵���� 
 * ���ߣ� sxs
 * ����ʱ�䣺2013-11-26 ����11:40:31
 */
public class SnsManager {

	private static UMSocialService socialService; 
	private boolean haveConfiged;// �Ƿ��Ѿ��������
	final String des = "Weixin";
	private static final String WX_APP_ID = "wx85c19fdb21426346";
	private SnsPostListener callback;
	// �����洫���activity
	public static Context snsContext;
	public Activity snsActivity;
	
//	public String contentUrl = ConfigureManager.getInstance().webSiteUrl;
	public void setSnsContext( Context context )
	{
		snsContext = context;
	}
	public void setSnsActivity( final Activity activity )
	{
		snsActivity = activity;
		callback = new SnsPostListener() { 
			@Override
			public void onStart() {
			//Toast.makeText(activity, "����ʼ000", Toast.LENGTH_SHORT).show();
			}
			@Override
			public void onComplete(SHARE_MEDIA arg0, int arg1,
					SocializeEntity arg2) {
				// TODO Auto-generated method stub
				if(arg1 == 200 && updateDataListener != null){
					updateDataListener.onUpdateData();
				}
			}
		};
		socialService.registerListener(callback);
	}
	private OnUpdateDataListener updateDataListener;
	public OnUpdateDataListener getUpdateDataListener() {
		return updateDataListener;
	}

	public void setUpdateDataListener(OnUpdateDataListener updateDataListener) {
		this.updateDataListener = updateDataListener;
	}
	
	
	// ΢�Ź����࣬��΢��sdk��libammsdk.jar���ṩ
//	public IWXAPI api;
	
	private static SnsManager _instance;
	public static SnsManager getInstance( )
	{
		if(_instance == null )
		{
			_instance = new SnsManager();
		}
		return _instance;
	}
	public SnsManager()
	{
		super();
		socialService = UMServiceFactory.getUMSocialService("com.umeng.share", RequestType.SOCIAL);
	}
	
	
	private String contentUrl;
	// ����Ҫ�����ƽ̨ ���ˡ���Ѷ��΢�ţ����͸����Ѻ�����Ȧ��
	public void configPlatforms(String url)
	{
		if( !haveConfiged )
		{
			socialService.getConfig().removePlatform( SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN, SHARE_MEDIA.QZONE, SHARE_MEDIA.SMS);
			socialService.getConfig().setPlatformOrder(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
													 SHARE_MEDIA.TENCENT, SHARE_MEDIA.SINA);
					  
			socialService.getConfig().setShareMail(false);
			socialService.getConfig().setSsoHandler(new SinaSsoHandler());
			haveConfiged = true;
		}
		configWeixin(url);
	}
	
	//��������
	public void setShareContent(String content) {
		UMWXHandler wxHandler = socialService.getConfig().supportWXPlatform(snsActivity, WX_APP_ID, contentUrl); 
		wxHandler.setCircleTitle(content);
		socialService.setShareContent(content);
	}
	
	public void setSsoHandler(UMSsoHandler handler) {
		socialService.getConfig().setSsoHandler(handler);
	}
	
	public void openShare(String shareText) {
		setWeixinContent( contentUrl , shareText );
		socialService.setShareContent(shareText);
		socialService.openShare(snsActivity, false);
	}
	
	public UMSsoHandler getSsoHandler(int resultCode) {
		return socialService.getConfig().getSsoHandler(resultCode);
	}
	
	private void setWeixinContent(String url,String text){
		Bitmap bitmap = BitmapFactory.decodeResource(snsActivity.getResources(), R.drawable.ic_launcher);
		
		UMImage mUMImgBitmap = new UMImage(snsActivity,bitmap);
		WeiXinShareContent weixinContent = new WeiXinShareContent(mUMImgBitmap);
	    weixinContent.setShareContent(text);
	    weixinContent.setTargetUrl(url);
	    weixinContent.setTitle(snsActivity.getResources().getString(R.string.app_name));
	    socialService.setShareMedia(weixinContent);

		// ��������Ȧ���������
		CircleShareContent circleMedia = new CircleShareContent(new UMImage(snsActivity, bitmap));
		circleMedia.setTitle(text);
		circleMedia.setShareContent(text);
		circleMedia.setTargetUrl(url);
		socialService.setShareMedia(circleMedia);
	}
	
	private void configWeixin(String url) {
		contentUrl = url;
		// ���΢��ƽ̨������1Ϊ��ǰActivity, ����2Ϊ�û������AppID, ����3Ϊ�������������ת����Ŀ��url
		UMWXHandler wxHandler = socialService.getConfig().supportWXPlatform(snsActivity, WX_APP_ID, contentUrl);
		wxHandler.setWXTitle(snsActivity.getResources().getString(R.string.app_name));
		// ֧��΢������Ȧ
		socialService.getConfig().supportWXCirclePlatform(snsActivity, WX_APP_ID, contentUrl) ;
	}
	
	public void distroyActivity(){
		socialService.unregisterListener(callback);
	}

}
