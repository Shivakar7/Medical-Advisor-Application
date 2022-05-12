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
import medicaladvisor.com.medicaladvisor.R;
import medicaladvisor.com.medicaladvisor.ViewDetailsActivity;

public class DiseaseAdapter extends BaseAdapter{

    Context context;
    ArrayList<DiseaseModel> diseasesArrayList;
    private ArrayList<DiseaseModel> arrayList;

    public DiseaseAdapter(Context context, ArrayList<DiseaseModel> diseasesArrayList) {
        this.context = context;
        this.diseasesArrayList = diseasesArrayList;
        this.arrayList = new ArrayList<DiseaseModel>();
        this.arrayList.addAll(this.diseasesArrayList);
    }

    @Override
    public int getCount() {
        return diseasesArrayList.size();
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
        String name = diseasesArrayList.get(position).getName();
        tutorText.setText(name);
        convertView.setTag(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=Integer.parseInt(v.getTag().toString());
                DiseaseModel diseaseModel=diseasesArrayList.get(pos);
                Log.v("Data",diseaseModel.getName());
                Gson gson = new Gson();
                String diseaseJson = gson.toJson(diseaseModel);
                Intent intent=new Intent(context, ViewDetailsActivity.class);
                intent.putExtra("VIEW_TYPE","DISEASE");
                intent.putExtra("DiseaseData",diseaseJson);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        diseasesArrayList.clear();
        if (charText.length() == 0) {
            diseasesArrayList.addAll(arrayList);
        } else {
            for (DiseaseModel disease : arrayList) {
                if (disease.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    diseasesArrayList.add(disease);
                }
            }
        }
        notifyDataSetChanged();
    }
}
