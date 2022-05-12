package medicaladvisor.com.medicaladvisor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Locale;

import medicaladvisor.com.medicaladvisor.Model.BloodModel;
import medicaladvisor.com.medicaladvisor.Model.HospitalModel;
import medicaladvisor.com.medicaladvisor.R;
import medicaladvisor.com.medicaladvisor.ViewDetailsActivity;

public class BloodAdapter  extends BaseAdapter {

    Context context;
    ArrayList<BloodModel> bloodArrayList;
    private ArrayList<BloodModel> arrayList;

    public BloodAdapter(Context context, ArrayList<BloodModel> bloodArrayList) {
        this.context = context;
        this.bloodArrayList = bloodArrayList;
        this.arrayList = new ArrayList<BloodModel>();
        this.arrayList.addAll(this.bloodArrayList);
    }

    @Override
    public int getCount() {
        return bloodArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_name, null);
        TextView tutorText = convertView.findViewById(R.id.name);
        String name = bloodArrayList.get(position).getDonnerName();
        tutorText.setText(name);
        convertView.setTag(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=Integer.parseInt(v.getTag().toString());
                BloodModel bloodModel=bloodArrayList.get(pos);
                Log.v("Data",bloodModel.getDonnerName());
                Gson gson = new Gson();
                String bloodJson = gson.toJson(bloodModel);
                Intent intent=new Intent(context, ViewDetailsActivity.class);
                intent.putExtra("VIEW_TYPE","BLOOD");
                intent.putExtra("BloodData",bloodJson);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        bloodArrayList.clear();
        if (charText.length() == 0) {
            bloodArrayList.addAll(arrayList);
        } else {
            for (BloodModel blood : arrayList) {
                if (blood.getDonnerName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    bloodArrayList.add(blood);
                }
            }
        }
        notifyDataSetChanged();
    }
}

