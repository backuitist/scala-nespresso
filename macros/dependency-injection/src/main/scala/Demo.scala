import com.softwaremill.macwire.Macwire

object Demo {

    case class Person(name: String)

    class PersonIndexer(person: Person) {
        def index(): Unit = {
            println("Indexing... found person : " + person)
        }
    }

    class Runner(indexer: PersonIndexer) {
        def run(): Unit = {
            println("Running!")
            indexer.index()
        }
    }

    trait ModuleA extends Macwire {
        val name: String // abstract member for module parameter

        val person : Person = wire[Person]
        val indexer : PersonIndexer = wire[PersonIndexer]
    }

    // use trait inheritance to create a module dependency
    trait ModuleB extends Macwire with ModuleA {
        val runner : Runner = wire[Runner]
    }

    class MainModule(val name : String) extends ModuleB

    def main(args: Array[String]) {
        new MainModule("john").runner.run()

        // alternatively we can create an anonymous class and directly fill in the
        // missing members
        new ModuleB {
            val name = "john"
        }.runner.run()
    }
}