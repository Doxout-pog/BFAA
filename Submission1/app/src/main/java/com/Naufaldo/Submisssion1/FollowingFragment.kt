package com.Naufaldo.Submisssion1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_following.*
import org.json.JSONArray
import org.json.JSONObject

class FollowingFragment: Fragment() {

    private var listUser: ArrayList<User> = ArrayList()
    private lateinit var adapter: Adapter
    private lateinit var rvUsers: RecyclerView

    companion object {
        private val TAG = FollowingFragment::class.java.simpleName
        const val EXTRA_USER = "EXTRA_USER"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvUsers= recycleViewFollowing
        rvUsers.setHasFixedSize(true)

        val listFollowing = activity!!.intent.getParcelableExtra<User>(FollowersFragment.EXTRA_USER) as User
        if (listFollowing != null) {
            getUserFollowing(listFollowing.userName.toString())
        }
        else listFollowing?.let { getUserFollowing(it) }
        showRecyclerList()
    }

    private fun getUserFollowing(id: String) {
        progressBarFollowing.visibility= View.VISIBLE

        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$id/following"
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        progressBarFollowing.visibility= View.VISIBLE

        client.get(url, object: AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray){
                progressBarFollowing.visibility = View.INVISIBLE
                val listUser = ArrayList<User>()
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonArray = JSONArray(result)

                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val username = jsonObject.getString("login")
                        val cvphoto = jsonObject.getString("avatar_url")
                        val NUser = User()
                        NUser.userName = username
                        NUser.avatar = cvphoto
                        listUser.add(NUser)
                        val adapter = Adapter(listUser)
                        rvUsers.adapter= adapter
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable) {
                progressBarFollowing.visibility = View.INVISIBLE

                val errorMessage = when (statusCode) {
                    401 -> "$statusCode: Bad Request"
                    403 -> "$statusCode: Forbidden"
                    404 -> "$statusCode: Not Found"
                    else -> "$statusCode: ${error.message}"
                }
                Log.d("Exception", errorMessage)
            }
        })
    }

    private fun showRecyclerList() {
        rvUsers.layoutManager = LinearLayoutManager(activity)
        val listGitsAdapter = Adapter(listUser)
        rvUsers.adapter = listGitsAdapter
        listGitsAdapter.setOnItemClickCallback(object: Adapter.OnItemClickCallback {
            override fun onItemClicked(User: User) {
            }
        })
    }
}
