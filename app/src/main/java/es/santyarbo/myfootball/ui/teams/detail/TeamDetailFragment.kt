package es.santyarbo.myfootball.ui.teams.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import es.santyarbo.myfootball.databinding.TeamDetailFragmentBinding
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.core.parameter.parametersOf

class TeamDetailFragment : Fragment() {

    private val viewModel: TeamDetailViewModel by lifecycleScope.viewModel(this) {
        parametersOf(args.id)
    }
    private lateinit var binding: TeamDetailFragmentBinding
    private val args: TeamDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TeamDetailFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(binding) {
            viewmodel = viewModel
            lifecycleOwner = this@TeamDetailFragment
        }
    }
}