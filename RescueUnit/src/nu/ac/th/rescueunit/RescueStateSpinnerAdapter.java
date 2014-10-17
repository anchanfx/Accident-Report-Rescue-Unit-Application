package nu.ac.th.rescueunit;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class RescueStateSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
	private List<RescueState> listOfRescueState;
	private LayoutInflater inflater;
	
	public RescueStateSpinnerAdapter(Context context, List<RescueState> listOfRescueState) {
		this.listOfRescueState = listOfRescueState;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setCancelable(true);
	}

	@Override
	public int getCount() {
		return listOfRescueState.size();
	}

	@Override
	public Object getItem(int position) {
		return listOfRescueState.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
	    return getCustomView(position, convertView, parent);
	}

	public View getCustomView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		
		if(convertView == null) {
	    	vi = inflater.inflate(R.layout.state_spinner_menu, parent, false);
	    }
	
	    TextView txtView_accident = (TextView)vi.findViewById(R.id.txtview_state);
	    txtView_accident.setText(listOfRescueState.get(position).getName());
	    
	    return vi;
	}
	
	public void clear() {
		listOfRescueState.clear();
	}
	
	public void addAll(List<RescueState> listOfRescueState) {
		this.listOfRescueState = listOfRescueState;
	}
}
