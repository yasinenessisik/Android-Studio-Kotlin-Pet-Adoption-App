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
    lateinit var img_dog:ImageView;
    lateinit var img_cat:ImageView;
    lateinit var img_rabbit:ImageView;
    lateinit var img_fish:ImageView;
    lateinit var img_bird:ImageView;
    lateinit var img_other:ImageView;
    var dog_selected =false
    var cat_selected =false
    var fish_selected=false
    var rabbit_selected=false
    var bird_selected=false
    var other_selected=false
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


        img_dog=rootView.findViewById<ImageView>(R.id.img_dog)

        img_dog.setOnClickListener(View.OnClickListener {
            changeVisibility(0)

        })
        img_cat=rootView.findViewById<ImageView>(R.id.img_cat)
        img_cat.setOnClickListener(View.OnClickListener {
            changeVisibility(1)


        })
        img_rabbit=rootView.findViewById<ImageView>(R.id.img_rabbit)
        img_rabbit.setOnClickListener(View.OnClickListener {

            changeVisibility(2)

        })
        img_fish=rootView.findViewById<ImageView>(R.id.img_fish)
        img_fish.setOnClickListener(View.OnClickListener {
            changeVisibility(3)

        })

        img_bird=rootView.findViewById<ImageView>(R.id.img_bird)
        img_bird.setOnClickListener(View.OnClickListener {
            changeVisibility(4)


        })
        img_other=rootView.findViewById<ImageView>(R.id.img_other)
        img_other.setOnClickListener(View.OnClickListener {

            changeVisibility(5)

        })
        return rootView;
    }

    private fun changeVisibility(id:Int) {
        if(id==0){
            //for dog
            if(dog_selected){
                img_dog.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                img_dog.setImageResource(R.drawable.dog)
                dog_selected=false


            }
            else{
                img_dog.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_selected_image)
                img_dog.setImageResource(R.drawable.white_dog)
                if (cat_selected){
                    img_cat.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_cat.setImageResource(R.drawable.cat)
                    cat_selected=false
                }
                if (rabbit_selected){
                    img_rabbit.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_rabbit.setImageResource(R.drawable.rabbit)
                    rabbit_selected=false
                }
                if (fish_selected){
                    img_fish.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_fish.setImageResource(R.drawable.fish)
                    fish_selected=false
                }
                if (bird_selected){
                    img_bird.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_bird.setImageResource(R.drawable.bird)
                    bird_selected=false
                }
                if (other_selected){
                    img_other.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_other.setImageResource(R.drawable.other)
                    other_selected=false
                }
                dog_selected=true
            }

        }
        else if(id==1){
            //for cat
            if(cat_selected){
                img_cat.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                img_cat.setImageResource(R.drawable.cat)
                cat_selected=false


            }
            else{
                img_cat.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_selected_image)
                img_cat.setImageResource(R.drawable.white_cat)
                if (dog_selected){
                    img_dog.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_dog.setImageResource(R.drawable.dog)
                    dog_selected=false
                }
                if (rabbit_selected){
                    img_rabbit.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_rabbit.setImageResource(R.drawable.rabbit)
                    rabbit_selected=false
                }
                if (fish_selected){
                    img_fish.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_fish.setImageResource(R.drawable.fish)
                    fish_selected=false
                }
                if (bird_selected){
                    img_bird.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_bird.setImageResource(R.drawable.bird)
                    bird_selected=false
                }
                if (other_selected){
                    img_other.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_other.setImageResource(R.drawable.other)
                    other_selected=false
                }
                cat_selected=true
            }

        }
        else if(id==2){//for rabbit
            //for rabbit
            if(rabbit_selected){
                img_rabbit.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                img_rabbit.setImageResource(R.drawable.rabbit)
                rabbit_selected=false

            }
            else{
                img_rabbit.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_selected_image)
                img_rabbit.setImageResource(R.drawable.white_rabbit)
                if (cat_selected){
                    img_cat.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_cat.setImageResource(R.drawable.cat)
                    cat_selected=false
                }
                if (dog_selected){
                    img_dog.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_dog.setImageResource(R.drawable.dog)
                    dog_selected=false
                }
                if (fish_selected){
                    img_fish.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_fish.setImageResource(R.drawable.fish)
                    fish_selected=false
                }
                if (bird_selected){
                    img_bird.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_bird.setImageResource(R.drawable.bird)
                    bird_selected=false
                }
                if (other_selected){
                    img_other.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_other.setImageResource(R.drawable.other)
                    other_selected=false
                }

                rabbit_selected=true
            }

        }
        else if (id==3){
            //for fish
            if(fish_selected){
                img_fish.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                img_fish.setImageResource(R.drawable.fish)
                fish_selected=false

            }
            else{
                img_fish.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_selected_image)
                img_fish.setImageResource(R.drawable.white_fish)
                if (cat_selected){
                    img_cat.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_cat.setImageResource(R.drawable.cat)
                    cat_selected=false
                }
                if (rabbit_selected){
                    img_rabbit.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_rabbit.setImageResource(R.drawable.rabbit)
                    rabbit_selected=false
                }
                if (dog_selected){
                    img_dog.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_dog.setImageResource(R.drawable.dog)
                    dog_selected=false
                }
                if (bird_selected){
                    img_bird.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_bird.setImageResource(R.drawable.bird)
                    bird_selected=false
                }
                if (other_selected){
                    img_other.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_other.setImageResource(R.drawable.other)
                    other_selected=false
                }

                fish_selected=true
            }

        }
        else if (id==4){
            //for bird
            if(bird_selected){
                img_bird.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                img_bird.setImageResource(R.drawable.bird)
                bird_selected=false

            }
            else{
                img_bird.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_selected_image)
                img_bird.setImageResource(R.drawable.white_bird)
                if (cat_selected){
                    img_cat.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_cat.setImageResource(R.drawable.cat)
                    cat_selected=false
                }
                if (rabbit_selected){
                    img_rabbit.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_rabbit.setImageResource(R.drawable.rabbit)
                    rabbit_selected=false
                }
                if (fish_selected){
                    img_fish.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_fish.setImageResource(R.drawable.fish)
                    fish_selected=false
                }
                if (dog_selected){
                    img_dog.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_dog.setImageResource(R.drawable.dog)
                    bird_selected=false
                }
                if (other_selected){
                    img_other.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_other.setImageResource(R.drawable.other)
                    other_selected=false
                }

                bird_selected=true
            }

        }
        else {
            //for other
            if(other_selected){
                img_other.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                img_other.setImageResource(R.drawable.other)
                other_selected=false

            }
            else{
                img_other.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_selected_image)
                img_other.setImageResource(R.drawable.white_other)
                if (cat_selected){
                    img_cat.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_cat.setImageResource(R.drawable.cat)
                    cat_selected=false
                }
                if (rabbit_selected){
                    img_rabbit.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_rabbit.setImageResource(R.drawable.rabbit)
                    rabbit_selected=false
                }
                if (fish_selected){
                    img_fish.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_fish.setImageResource(R.drawable.fish)
                    fish_selected=false
                }
                if (bird_selected){
                    img_bird.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_bird.setImageResource(R.drawable.bird)
                    bird_selected=false
                }
                if (dog_selected){
                    img_dog.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                    img_dog.setImageResource(R.drawable.dog)
                    dog_selected=false
                }

                other_selected=true
            }

        }


    }




}