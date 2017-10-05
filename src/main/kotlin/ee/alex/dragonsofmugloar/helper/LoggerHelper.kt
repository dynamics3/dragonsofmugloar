@file:JvmName(name = "LoggerHelper")

package ee.alex.dragonsofmugloar.helper

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

/**
 * @author Aleksei Kulitškov
 */
fun <T : Any> loggerFor(clazz: KClass<T>): Logger = LoggerFactory.getLogger(clazz.java)
