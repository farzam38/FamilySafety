package com.example.familysafety

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MemberAdapter(private val listMember: List<MemberModel>) : RecyclerView.Adapter<MemberAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_member, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val member = listMember[position]
        holder.textName.text = member.name
        holder.address.text = member.address
        holder.time.text = member.time
        holder.battery.text = "40"
        holder.distance.text = "23232"
        holder.wifi.text = "YES"


    }

    override fun getItemCount(): Int {

        return listMember.size

    }


    inner class ViewHolder( item: View) : RecyclerView.ViewHolder(item) {

        val textName = item.findViewById<TextView>(R.id.name)
        val address = item.findViewById<TextView>(R.id.address)
        val time = item.findViewById<TextView>(R.id.time)
        val battery = item.findViewById<TextView>(R.id.battery_percent)
        val distance = item.findViewById<TextView>(R.id.distance_value)
        val wifi = item.findViewById<TextView>(R.id.wifi_value)



    }

}
