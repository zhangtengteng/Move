package com.xuanit.move.adapter;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.ab.bitmap.AbImageDownloader;
import com.xuanit.move.R;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.model.GameInfo;
import com.xuanit.move.util.StringUtils;

import android.content.DialogInterface;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



@TargetApi(Build.VERSION_CODES.CUPCAKE) public class GameAdapter extends BaseAdapter {
	private Context context;
	private List<GameInfo> list = new ArrayList<GameInfo>();
	private LayoutInflater inflater;
	private AbImageDownloader mAbImageDownloader;
	private boolean loading=false;
	
	public GameAdapter(Context context, List<GameInfo> list) {
		System.out.println("=====GameAdapter()====");
		this.context = context;
		this.list = list;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if (mAbImageDownloader == null) {
			mAbImageDownloader = new AbImageDownloader(context);
		}
		if (convertView == null) {

			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_game, null);
			//游戏头像
			holder.uiv_item_game = (ImageView) convertView
					.findViewById(R.id.uiv_item_game);
			//游戏名字
			holder.tv_game_name = (TextView) convertView
					.findViewById(R.id.tv_game_name);
			//游戏种类
			holder.tv_sort = (TextView) convertView.findViewById(R.id.tv_sort);
			//游戏大小
			holder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);
			//游戏下载数
			holder.tv_load_num = (TextView) convertView
					.findViewById(R.id.tv_load_num);
			//游戏积分
			holder.tv_game_point = (TextView) convertView
					.findViewById(R.id.tv_GameIntegral);
			
			//游戏下载进度条
			holder.pb_game_download = (ProgressBar) convertView.findViewById(R.id.down_pb);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final GameInfo gameInfo = list.get(position);
		//游戏头像
		if (!StringUtils.isNullOrEmpty(gameInfo.Src)) {
			if(gameInfo.Src.startsWith("~")){
				String replace = gameInfo.Src.replace("~", AppConfig.HOSTURL);
				
				mAbImageDownloader.display(holder.uiv_item_game, replace);
				//holder.uiv_item_game.setUrl(replace);
			}else{
				mAbImageDownloader.display(holder.uiv_item_game, gameInfo.Src);
				//holder.uiv_item_game.setUrl(gameInfo.Src);
			}
		}
		//游戏名字
		if (!StringUtils.isNullOrEmpty(gameInfo.Name)) {
			holder.tv_game_name.setText(gameInfo.Name);
		}
		//游戏种类
		if (!StringUtils.isNullOrEmpty(gameInfo.GameType)) {
			holder.tv_sort.setText(gameInfo.GameType);
		}
		//游戏大小
		if (!StringUtils.isNullOrEmpty(gameInfo.GameSize)) {
			holder.tv_size.setText(gameInfo.GameSize + "M");
		}
		//游戏下载数
		if (!StringUtils.isNullOrEmpty(gameInfo.WeeKDownTimes)) {
			holder.tv_load_num.setText(gameInfo.WeeKDownTimes);
		}
		//游戏积分
		if (!StringUtils.isNullOrEmpty(gameInfo.GameIntegral)) {
			holder.tv_game_point.setText(gameInfo.GameIntegral);
		}
		
		final ProgressBar tempProgressBar=holder.pb_game_download;
		//点击下载

		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final FileLoadTask downTask=new FileLoadTask(tempProgressBar);
					if(!loading){
						AlertDialog.Builder builder=new AlertDialog.Builder(context);
						builder.setTitle("下载提示").setMessage("你确定要下载该游戏");
						builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								if(!StringUtils.isNullOrEmpty(gameInfo.GameUrl)){
									tempProgressBar.setVisibility(View.VISIBLE);
									tempProgressBar.setProgress(0);
									downTask.execute(gameInfo.GameUrl);
								}else{
									Toast.makeText(context, "下载出错", Toast.LENGTH_SHORT).show();
								}
							}
						});
						builder.setNegativeButton("取消",  new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								arg0.dismiss();
							}
							
						});
						builder.show();
					}else{
						AlertDialog.Builder builder=new AlertDialog.Builder(context);
						builder.setTitle("下载取消提示").setMessage("你确定要取消下载该游戏");
						builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								downTask.cancel(true);
							}
						});
						builder.setNegativeButton("取消",  new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								arg0.dismiss();
							}
							
						});
						builder.show();
					}
			
				
				
			}
		});

		
		
		return convertView;
	}

	class ViewHolder {
		private ImageView uiv_item_game;  //游戏头像
		private TextView tv_game_name;       //游戏名字
		private TextView tv_sort;            //游戏种类
		private TextView tv_size;            //游戏大小
		private TextView tv_load_num;        //游戏下载数
		private TextView tv_game_point;      //游戏积分
		private ProgressBar pb_game_download;//游戏下载进度条
	}
	
	//游
	class FileLoadTask extends AsyncTask<String, Integer, String> {
		
		private ProgressBar progressBar;
		
		public FileLoadTask(ProgressBar progressBar) {
			super();
			this.progressBar = progressBar;
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			loading=true;
			super.onPreExecute();
			
			Toast.makeText(context, "后台下载中...", Toast.LENGTH_SHORT).show();
		}
		@Override
		protected String doInBackground(String... strings) {
			String url = strings[0];
			File file = null;
			try {
				URL aURL = new URL(url);
				URLConnection conn = aURL.openConnection();
				conn.connect();
				InputStream is = conn.getInputStream();
			
				file = new File(Environment.getExternalStorageDirectory(),
						url.substring(url.lastIndexOf("/")));
				FileOutputStream fos = new FileOutputStream(file);
				BufferedInputStream bis = new BufferedInputStream(is);
				byte[] buffer = new byte[1024];
				int len;
				int size = 0;
				while ((len = bis.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
					size = size + len;
					publishProgress(size* 100 / conn.getContentLength());
				}
				fos.close();
				bis.close();
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return file.getAbsoluteFile().toString();
		}

		@Override
		protected void onPostExecute(String path) {
			loading=false;
			progressBar.setVisibility(View.GONE);
			Toast.makeText(context, "下载路径："+ path, Toast.LENGTH_SHORT).show();
			
			//安装apk文件
			String fileName = path;
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(new File(fileName)),
					"application/vnd.android.package-archive");
			
			context.startActivity(intent);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			progressBar.setProgress(values[0]);
			// mProgressBar.setProgress(values[0]);
//			tv.setText(values[0]+"Kb");
//			progressBar1.setProgress((values[0]*100)/1000);
		}
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onCancelled()
		 */
		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
			loading=false;
			progressBar.setVisibility(View.GONE);
		}	
	}
}
