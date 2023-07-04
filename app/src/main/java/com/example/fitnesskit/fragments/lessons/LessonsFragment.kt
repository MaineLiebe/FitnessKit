package com.example.fitnesskit.fragments.lessons

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.fitnesskit.R
import com.example.fitnesskit.databinding.FragmentLessonsBinding
import com.example.fitnesskit.model.Lesson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LessonsFragment : Fragment() {

    private var _binding: FragmentLessonsBinding? = null
    private val binding get() = _binding!!

    private val adapter = LessonsAdapter{
        findNavController().navigate(LessonsFragmentDirections.openTraining(it))
    }

    private val viewModel: LessonsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLessonsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter
        viewModel.lessons.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

    }

}