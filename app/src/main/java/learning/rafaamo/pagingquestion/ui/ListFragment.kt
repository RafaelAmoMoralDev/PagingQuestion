package learning.rafaamo.pagingquestion.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import learning.rafaamo.pagingquestion.databinding.FragmentListBinding

@AndroidEntryPoint
class ListFragment : Fragment() {

  private val viewModel: ItemViewModel by viewModels()

  private lateinit var binding: FragmentListBinding
  private val pagingAdapter = ItemAdapter(ItemAdapter.ItemComparator)

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = FragmentListBinding.inflate(layoutInflater).apply {
      list.adapter = pagingAdapter
    }
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    lifecycleScope.launch {
      viewModel.pagingDataFlow.collectLatest { pagingData ->
        pagingAdapter.submitData(pagingData)
      }
    }
  }

}