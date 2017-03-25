package com.csu.client;


import com.csu.service.MainService;
import com.csu.service.Task;
import com.csu.util.Constant;
import com.example.client.R;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class QustionFragment extends Fragment {
	private Button questSend;
	private EditText questionView;
	private ClickListener clickListener = new ClickListener();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View newsLayout = inflater.inflate(R.layout.qustion_layout, container,false);
		questionView = (EditText)newsLayout.findViewById(R.id.questionText);
		questSend = (Button)newsLayout.findViewById(R.id.btn_question);
		questSend.setOnClickListener(clickListener);
		return newsLayout;
	}

	public class ClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
		 	switch(v.getId()){
			case R.id.btn_question:
				String text = String.valueOf(questionView.getText());
				if(text.equals("")){
					new AlertDialog.Builder(getActivity()).setMessage("�Բ�����������Ϊ�գ���������")
					.setPositiveButton("ȷ��", null).show();
				}else{
					Task task = new Task(Constant.Task_Question_Post,"������");
					task.addData(text);
					MainService.addTask(task);
					questionView.setText("");
				}
				break;
		};
		}
	};
}
