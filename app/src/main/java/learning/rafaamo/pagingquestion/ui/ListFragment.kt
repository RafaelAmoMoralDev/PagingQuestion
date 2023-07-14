package learning.rafaamo.pagingquestion.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import learning.rafaamo.pagingquestion.databinding.FragmentListBinding
import learning.rafaamo.pagingquestion.databinding.FragmentProfileBinding

@AndroidEntryPoint
class ListFragment : Fragment() {

  private val viewModel: ItemViewModel by viewModels()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
   return FragmentListBinding.inflate(layoutInflater).root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val binding = FragmentListBinding.bind(view)
    val adapter = ItemAdapter(ItemAdapter.ItemComparator)
    binding.list.adapter = adapter

    // postpone and start collecting data
    postponeEnterTransition()

    lifecycleScope.launch {
      viewModel.pagingDataFlow.collectLatest { pagingData ->
        adapter.submitData(pagingData)
      }
    }

    // wait till Pages are updated with data
    adapter.addOnPagesUpdatedListener {
      // doOnPreDraw here
      binding.list.doOnPreDraw {
        // no op if not postponed
        startPostponedEnterTransition()
      }
    }
  }

}