package com.yasinenessisik.adopt_a_pet

import android.graphics.Color
import android.graphics.PorterDuff
import android.media.Image
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat


class SearchFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView: View
        rootView=inflater.inflate(R.layout.fragment_search, container, false);
        var dog_selected =false
        var cat_selected =false
        var fish_selected=false
        var rabbit_selected=false
        var bird_selected=false
        var other_selected=false

        var img_dog=rootView.findViewById<ImageView>(R.id.img_dog)

        img_dog.setOnClickListener(View.OnClickListener {
            dog_selected=changeVisibility(0,img_dog,dog_selected)

        })
        var img_cat=rootView.findViewById<ImageView>(R.id.img_cat)
        img_cat.setOnClickListener(View.OnClickListener {

            cat_selected=changeVisibility(1,img_cat,cat_selected)

        })
        var img_rabbit=rootView.findViewById<ImageView>(R.id.img_rabbit)
        img_rabbit.setOnClickListener(View.OnClickListener {

            rabbit_selected=changeVisibility(2,img_rabbit,rabbit_selected)

        })
        var img_fish=rootView.findViewById<ImageView>(R.id.img_fish)
        img_fish.setOnClickListener(View.OnClickListener {
            fish_selected=changeVisibility(3,img_fish,fish_selected)

        })

        var img_bird=rootView.findViewById<ImageView>(R.id.img_bird)
        img_bird.setOnClickListener(View.OnClickListener {

            bird_selected=changeVisibility(4,img_bird,bird_selected)

        })
        var img_other=rootView.findViewById<ImageView>(R.id.img_other)
        img_other.setOnClickListener(View.OnClickListener {

            other_selected=changeVisibility(5,img_other,other_selected)

        })
        return rootView;
    }

    private fun changeVisibility(id:Int,mainImg:ImageView,selected:Boolean): Boolean {
        if(id==0){
            //for dog
            if(selected){
                mainImg.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                mainImg.setImageResource(R.drawable.dog)
            }
            else{
                mainImg.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_selected_image)
                mainImg.setImageResource(R.drawable.white_dog)
            }

        }
        else if(id==1){
            //for cat
            if(selected){
                mainImg.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                mainImg.setImageResource(R.drawable.cat)
            }
            else{
                mainImg.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_selected_image)
                mainImg.setImageResource(R.drawable.white_cat)
            }

        }
        else if(id==2){
            //for rabbit
            if(selected){
                mainImg.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                mainImg.setImageResource(R.drawable.rabbit)
            }
            else{
                mainImg.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_selected_image)
                mainImg.setImageResource(R.drawable.white_rabbit)
            }

        }
        else if (id==3){
            //for fish
            if(selected){
                mainImg.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                mainImg.setImageResource(R.drawable.fish)
            }
            else{
                mainImg.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_selected_image)
                mainImg.setImageResource(R.drawable.white_fish)
            }

        }
        else if (id==4){
            //for bird
            if(selected){
                mainImg.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                mainImg.setImageResource(R.drawable.bird)
            }
            else{
                mainImg.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_selected_image)
                mainImg.setImageResource(R.drawable.white_bird)
            }

        }
        else {
            //for other
            if(selected){
                mainImg.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                mainImg.setImageResource(R.drawable.other)
            }
            else{
                mainImg.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_selected_image)
                mainImg.setImageResource(R.drawable.white_other)
            }

        }
        return !selected
        //deneme
    }




}