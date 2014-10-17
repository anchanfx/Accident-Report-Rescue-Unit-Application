package nu.ac.th.rescueunit;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AccidentListAdapter extends BaseAdapter {
	private List<AccidentWithState> listOfAccidentWithState;
	private LayoutInflater inflater;
	
	public AccidentListAdapter(Context context, List<AccidentWithState> listOfAccidentWithState) {
		this.listOfAccidentWithState = listOfAccidentWithState;
		inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return listOfAccidentWithState.size();
	}

	@Override
	public Object getItem(int position) {
		return listOfAccidentWithState.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		
	    if(convertView == null) {
	    	vi = inflater.inflate(R.layout.accident_list_item, parent, false);
	    }

	    
	    TextView txtView_accident = (TextView)vi.findViewById(R.id.txtview_accidentData);
	    txtView_accident.setText(listOfAccidentWithState.get(position).toString());

	    return vi;
	}
	
	public void clear() {
		listOfAccidentWithState.clear();
	}
	
	public void addAll(List<AccidentWithState> listOfAccidentWithState) {
		this.listOfAccidentWithState = listOfAccidentWithState;
	}
}
