package com.yasinenessisik.adopt_a_pet

import SearchFragmentAdapter
import android.graphics.Color
import android.graphics.PorterDuff
import android.media.Image
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yasinenessisik.adopt_a_pet.adapters.HomeReyclerAdapter


class SearchFragment : Fragment() {
    lateinit var img_dog:ImageView;
    lateinit var img_cat:ImageView;
    lateinit var img_rabbit:ImageView;
    lateinit var img_fish:ImageView;
    lateinit var img_bird:ImageView;
    lateinit var img_other:ImageView;
    lateinit var recyclerView:RecyclerView
    var dog_selected =false
    var cat_selected =false
    var fish_selected=false
    var rabbit_selected=false
    var bird_selected=false
    var other_selected=false
    var anything_selected=false
    lateinit var img_down_arrow:ImageButton
    var is_recyclerVisible=false
    private lateinit var recyclerViewAdapter: SearchFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View
        rootView=inflater.inflate(R.layout.fragment_search, container, false);

        val items = listOf("")
        recyclerViewAdapter = SearchFragmentAdapter(items)
        recyclerView=rootView.findViewById(R.id.sf_recyclerview)
        recyclerView.layoutManager=LinearLayoutManager(requireContext())
        img_down_arrow=rootView.findViewById(R.id.sf_down_arrow)
        recyclerView.visibility=View.GONE


        recyclerView.adapter=recyclerViewAdapter

        img_down_arrow.setOnClickListener(View.OnClickListener {

            setRecyclerState()

        })




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
                anything_selected=false


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
                anything_selected=true
            }

        }
        else if(id==1){
            //for cat
            if(cat_selected){
                img_cat.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                img_cat.setImageResource(R.drawable.cat)
                cat_selected=false
                anything_selected=false


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
                anything_selected=true
            }

        }
        else if(id==2){//for rabbit
            //for rabbit
            if(rabbit_selected){
                img_rabbit.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                img_rabbit.setImageResource(R.drawable.rabbit)
                rabbit_selected=false
                anything_selected=false

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
                anything_selected=true
            }

        }
        else if (id==3){
            //for fish
            if(fish_selected){
                img_fish.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                img_fish.setImageResource(R.drawable.fish)
                fish_selected=false
                anything_selected=false

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
                anything_selected=true
            }

        }
        else if (id==4){
            //for bird
            if(bird_selected){
                img_bird.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                img_bird.setImageResource(R.drawable.bird)
                bird_selected=false
                anything_selected=false

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
                anything_selected=true
            }

        }
        else {
            //for other
            if(other_selected){
                img_other.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_for_image)
                img_other.setImageResource(R.drawable.other)
                other_selected=false
                anything_selected=false

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
                anything_selected=true
            }

        }
        resetRecyclerState()
        setRecyclerData(id)

    }

    private fun setRecyclerData(id:Int) {
        Log.d("mymessage", "setRecyclerData: index is $id")
        if(id==0){
            var data= listOf("Kangal","Anadolu Çoban Köpeği","Pitbull","Rotweiller","Dogo argentino","Husskel")
            recyclerViewAdapter=SearchFragmentAdapter(data)
            recyclerView.adapter=recyclerViewAdapter
            recyclerViewAdapter.notifyDataSetChanged()


        }
        else if(id==1){
            var data= listOf("Sokak Kedisi","Ragdoll","Exotic","Persian","British Shorthair","Devon Rex")
            recyclerViewAdapter=SearchFragmentAdapter(data)
            recyclerView.adapter=recyclerViewAdapter
            recyclerViewAdapter.notifyDataSetChanged()
        }
        else if(id==2){
            var data= listOf("Cüce","Angora","Beveren","Havana","Himalaya","Argente")
            recyclerViewAdapter=SearchFragmentAdapter(data)
            recyclerView.adapter=recyclerViewAdapter
            recyclerViewAdapter.notifyDataSetChanged()
        }
        else if(id==3){
            var data= listOf("Beta","Barbus","Zebra Balığı","Ancistrus","Siyah Tetra","Moli","Lepistesler")
            recyclerViewAdapter=SearchFragmentAdapter(data)
            recyclerView.adapter=recyclerViewAdapter
            recyclerViewAdapter.notifyDataSetChanged()
        }
        else if(id==4){

            var data= listOf("Muhabbet Kuşu","Kanarya","Saka","Paraket","Papağan","Bülbül")
            recyclerViewAdapter=SearchFragmentAdapter(data)
            recyclerView.adapter=recyclerViewAdapter
            recyclerViewAdapter.notifyDataSetChanged()
        }

        else {
            var data= listOf("All")
            recyclerViewAdapter=SearchFragmentAdapter(data)
            recyclerView.adapter=recyclerViewAdapter
            recyclerViewAdapter.notifyDataSetChanged()
        }
    }


    private  fun setRecyclerState(){
        if(anything_selected){
            if(is_recyclerVisible){
                recyclerView.visibility=View.GONE
                img_down_arrow.setImageResource(R.drawable.down_arrow)
                img_down_arrow.setBackgroundResource(R.drawable.down_arrow)
                is_recyclerVisible=false



            }
            else{
                recyclerView.visibility=View.VISIBLE
                img_down_arrow.setImageResource(R.drawable.up_arrow)
                img_down_arrow.setBackgroundResource(R.drawable.up_arrow)
                is_recyclerVisible=true
            }
        }
        else{
            Log.d("mymessage", "setRecyclerState: first select item")
            recyclerView.visibility=View.GONE
            img_down_arrow.setImageResource(R.drawable.down_arrow)
            img_down_arrow.setBackgroundResource(R.drawable.down_arrow)
            is_recyclerVisible=false

        }
    }
    private  fun resetRecyclerState(){
        recyclerView.visibility=View.GONE
        img_down_arrow.setImageResource(R.drawable.down_arrow)
        img_down_arrow.setBackgroundResource(R.drawable.down_arrow)
        is_recyclerVisible=false
    }






}