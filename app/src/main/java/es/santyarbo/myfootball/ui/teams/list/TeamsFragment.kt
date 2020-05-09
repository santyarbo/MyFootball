package es.santyarbo.myfootball.ui.teams.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import es.santyarbo.myfootball.databinding.TeamsFragmentBinding
import es.santyarbo.myfootball.ui.common.EventObserver
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.core.parameter.parametersOf

class TeamsFragment : Fragment() {

    private val viewModel: TeamsViewModel by lifecycleScope.viewModel(this) {
        parametersOf(args.id)
    }
    private lateinit var binding: TeamsFragmentBinding
    private lateinit var navController: NavController
    private val args: TeamsFragmentArgs by navArgs()

    private lateinit var adapter: TeamsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TeamsFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()

        viewModel.navigateToDetail.observe(viewLifecycleOwner, EventObserver {
            val action = TeamsFragmentDirections.actionTeamsFragmentToTeamDetailFragment(it.id, args.id)
            navController.navigate(action)
        })

        adapter =
            TeamsAdapter(viewModel::onTeamClicked)
        with(binding) {
            recycler.adapter = adapter
            viewmodel = viewModel
            lifecycleOwner = this@TeamsFragment
        }
    }
}