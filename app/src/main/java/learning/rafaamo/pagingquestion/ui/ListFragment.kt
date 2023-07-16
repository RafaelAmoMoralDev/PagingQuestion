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

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    return FragmentListBinding.inflate(layoutInflater).root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val binding = FragmentProfileBinding.bind(view)
    val adapter = ItemAdapter(ItemAdapter.ItemComparator)

    // postpone and start collecting data
    postponeEnterTransition()

    // wait till Pages are updated with data
    adapter.addOnPagesUpdatedListener {
      binding.list.apply {
        // Check if the adapter is not set
        if (this.adapter == null) {
          // Set the adapter
          this.adapter = adapter

          // doOnPreDraw here
          doOnPreDraw {
            // no op if not postponed
            startPostponedEnterTransition()
          }
        }
      }
    }

    lifecycleScope.launch {
      viewModel.pagingDataFlow.collectLatest { pagingData ->
        adapter.submitData(pagingData)
      }
    }
  }

}