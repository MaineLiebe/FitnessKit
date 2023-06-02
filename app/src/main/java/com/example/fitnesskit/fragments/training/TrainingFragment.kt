package com.example.fitnesskit.fragments.training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.fitnesskit.R
import com.example.fitnesskit.databinding.FragmentTrainingBinding
import com.example.fitnesskit.model.UiLesson

class TrainingFragment : Fragment() {

    private var _binding: FragmentTrainingBinding? = null
    private val binding get() = _binding!!

    private val navArgs: TrainingFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrainingBinding.inflate(inflater, container, false)
        binding.trainingName.text = navArgs.training.name
        binding.description.text = navArgs.training.description
        binding.trainerName.text = navArgs.training.coachName
        Glide
            .with(binding.root.context)
            .load(navArgs.training.imageUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_assignment_24)
            .error(R.drawable.ic_baseline_account_box_24)
            .into(binding.imageAvatar)
        return binding.root
    }

}
