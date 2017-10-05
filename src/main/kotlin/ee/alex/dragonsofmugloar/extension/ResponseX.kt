@file:JvmName(name = "ResponseX")

package ee.alex.dragonsofmugloar.extension

import com.google.gson.Gson
import khttp.responses.Response

/**
 * @author Aleksei Kulitškov
 */
inline fun <reified T> Response.toModel(): T {
  return Gson().fromJson(this.jsonObject.toString(), T::class.java)
}
