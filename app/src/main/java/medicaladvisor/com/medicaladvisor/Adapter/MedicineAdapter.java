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
import medicaladvisor.com.medicaladvisor.Model.MedicineModel;
import medicaladvisor.com.medicaladvisor.R;
import medicaladvisor.com.medicaladvisor.ViewDetailsActivity;

public class MedicineAdapter extends BaseAdapter {

    Context context;
    ArrayList<MedicineModel> medicineArrayList;
    private ArrayList<MedicineModel> arrayList;

    public MedicineAdapter(Context context, ArrayList<MedicineModel> diseasesArrayList) {
        this.context = context;
        this.medicineArrayList = diseasesArrayList;
        this.arrayList = new ArrayList<MedicineModel>();
        this.arrayList.addAll(this.medicineArrayList);
    }

    @Override
    public int getCount() {
        return medicineArrayList.size();
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
        String name = medicineArrayList.get(position).getName();
        tutorText.setText(name);
        convertView.setTag(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=Integer.parseInt(v.getTag().toString());
                MedicineModel medicineModel=medicineArrayList.get(pos);
                Log.v("Data",medicineModel.getName());
                Gson gson = new Gson();
                String medicineJson = gson.toJson(medicineModel);
                Intent intent=new Intent(context, ViewDetailsActivity.class);
                intent.putExtra("VIEW_TYPE","MEDICINE");
                intent.putExtra("MedicineData",medicineJson);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        medicineArrayList.clear();
        if (charText.length() == 0) {
            medicineArrayList.addAll(arrayList);
        } else {
            for (MedicineModel medicine : arrayList) {
                if (medicine.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    medicineArrayList.add(medicine);
                }
            }
        }
        notifyDataSetChanged();
    }
}
