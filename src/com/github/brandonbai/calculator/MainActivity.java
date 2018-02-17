package com.caculator.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 
 * @author brandon
 * @since 2016/10/10
 */
public class MainActivity extends AppCompatActivity implements OnClickListener{
	private TextView mFormulaTextView;
	private TextView mResultTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}
	
	private void initView() {
		mFormulaTextView = (TextView) findViewById(R.id.display);
		mResultTextView = (TextView) findViewById(R.id.display_click);
		findViewById(R.id.digit0).setOnClickListener(this);
		findViewById(R.id.digit1).setOnClickListener(this);
		findViewById(R.id.digit2).setOnClickListener(this);
		findViewById(R.id.digit3).setOnClickListener(this);
		findViewById(R.id.digit4).setOnClickListener(this);
		findViewById(R.id.digit5).setOnClickListener(this);
		findViewById(R.id.digit6).setOnClickListener(this);
		findViewById(R.id.digit7).setOnClickListener(this);
		findViewById(R.id.digit8).setOnClickListener(this);
		findViewById(R.id.digit9).setOnClickListener(this);
		findViewById(R.id.dot).setOnClickListener(this);
		findViewById(R.id.mul).setOnClickListener(this);
		findViewById(R.id.sub).setOnClickListener(this);
		findViewById(R.id.add).setOnClickListener(this);
		findViewById(R.id.div).setOnClickListener(this);
		findViewById(R.id.bracket_left).setOnClickListener(this);
		findViewById(R.id.bracket_right).setOnClickListener(this);
		findViewById(R.id.clear).setOnClickListener(this);
		findViewById(R.id.backspace).setOnClickListener(this);
		findViewById(R.id.equal).setOnClickListener(this);
		
	}
	
	public void caculator() {
		String source =mResultTextView.getText().toString();
		// ��ʽ����ʱ�����м���
		if(!source.contains("(") && source.contains(")") 
				|| source.contains("(") && !source.contains(")")) {
			return;
		}
		mFormulaTextView.setText(source);
		
		String result = new Caculator().caculate(source+"#");
		mResultTextView.setText("0E-9".equals(result) ? "0" : result);
	}

	@Override
	public void onClick(View v) {
		String s = mResultTextView.getText().toString();
		if("0".equals(s) || "error".equals(s)) {
			s = "";
			mResultTextView.setText(s);
		} else if(s.matches("-\\d+.?\\d*")) {
			s = "(" + s + ")";
		}
		int id = v.getId();
		switch(id) {
		case R.id.digit0:
			mResultTextView.setText(s + "0");
			break;
		case R.id.digit1:
			mResultTextView.setText(s + "1");
			break;
		case R.id.digit2:
			mResultTextView.setText(s + "2");
			break;
		case R.id.digit3:
			mResultTextView.setText(s + "3");
			break;
		case R.id.digit4:
			mResultTextView.setText(s + "4");
			break;
		case R.id.digit5:
			mResultTextView.setText(s + "5");
			break;
		case R.id.digit6:
			mResultTextView.setText(s + "6");
			break;
		case R.id.digit7:
			mResultTextView.setText(s + "7");
			break;
		case R.id.digit8:
			mResultTextView.setText(s + "8");
			break;
		case R.id.digit9:
			mResultTextView.setText(s + "9");
			break;
		case R.id.dot:
			if("".equals(s)) {
				s = "0";
			}
			mResultTextView.setText(s + ".");
			break;
		case R.id.add:
			if("".equals(s)) {
				s = "0";
			}
			mResultTextView.setText(s + "+");
			break;
		case R.id.sub:
			if("".equals(s)) {
				s = "0";
			}
			mResultTextView.setText(s + "-");
			break;
		case R.id.mul:
			if("".equals(s)) {
				s = "0";
			}
			mResultTextView.setText(s + "��");
			break;
		case R.id.div:
			if("".equals(s)) {
				s = "0";
			}
			mResultTextView.setText(s + "��");
			break;
		case R.id.backspace:
			String ss = mResultTextView.getText().toString();
			if(ss.length() == 0) {
				ss = "0";
				mResultTextView.setText("0");
			}
			ss = ss.substring(0, ss.length()-1);
			mResultTextView.setText(ss);
			
			if(ss.length() == 0) {
				mResultTextView.setText("0");
			}
			break;
		case R.id.clear:
			mFormulaTextView.setText("");
			mResultTextView.setText("0");
			break;
		case R.id.bracket_left:
			mResultTextView.setText(s + "(");
			break;
		case R.id.bracket_right:
			mResultTextView.setText(s + ")");
			break;
		case R.id.equal:
			caculator();
			break;
			
			default:
				break;
		}
	}
}
