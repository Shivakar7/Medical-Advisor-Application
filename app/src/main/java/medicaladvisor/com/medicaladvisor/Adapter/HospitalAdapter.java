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

import medicaladvisor.com.medicaladvisor.Model.DiseaseModel;
import medicaladvisor.com.medicaladvisor.Model.HospitalModel;
import medicaladvisor.com.medicaladvisor.R;
import medicaladvisor.com.medicaladvisor.ViewDetailsActivity;

public class HospitalAdapter extends BaseAdapter {

    Context context;
    ArrayList<HospitalModel> hospitalArrayList;
    private ArrayList<HospitalModel> arrayList;

    public HospitalAdapter(Context context, ArrayList<HospitalModel> hospitalArrayList) {
        this.context = context;
        this.hospitalArrayList = hospitalArrayList;
        this.arrayList = new ArrayList<HospitalModel>();
        this.arrayList.addAll(this.hospitalArrayList);
    }

    @Override
    public int getCount() {
        return hospitalArrayList.size();
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
        String name = hospitalArrayList.get(position).getHospitalName();
        tutorText.setText(name);
        convertView.setTag(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=Integer.parseInt(v.getTag().toString());
                HospitalModel hospitalModel=hospitalArrayList.get(pos);
                Log.v("Data",hospitalModel.getHospitalName());
                Gson gson = new Gson();
                String hospitalJson = gson.toJson(hospitalModel);
                Intent intent=new Intent(context, ViewDetailsActivity.class);
                intent.putExtra("VIEW_TYPE","HOSPITAL");
                intent.putExtra("HospitalData",hospitalJson);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        hospitalArrayList.clear();
        if (charText.length() == 0) {
            hospitalArrayList.addAll(arrayList);
        } else {
            for (HospitalModel hospital : arrayList) {
                if (hospital.getHospitalName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    hospitalArrayList.add(hospital);
                }
            }
        }
        notifyDataSetChanged();
    }
}
