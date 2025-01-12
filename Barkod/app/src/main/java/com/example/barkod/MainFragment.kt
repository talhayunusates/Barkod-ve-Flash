package com.example.barkod

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.barkod.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    private val scanResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val resultData = result.data?.getStringExtra("SCAN_RESULT")
                resultData?.let {
                    viewModel.setResult(it)
                    findNavController().navigate(R.id.action_mainFragment_to_resultFragment)
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.btnFlash.setOnClickListener {
            toggleFlashlight()
        }

        binding.btnScan.setOnClickListener {
            val intent = Intent(requireContext(), ScannerActivity::class.java)
            scanResultLauncher.launch(intent)
        }

        return binding.root
    }

    private fun toggleFlashlight() {
        val intent = Intent(requireContext(), FlashlightService::class.java)
        intent.putExtra("ACTION", "TOGGLE")
        requireContext().startService(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
