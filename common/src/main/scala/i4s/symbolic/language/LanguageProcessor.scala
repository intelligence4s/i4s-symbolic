package i4s.symbolic.language

import i4s.symbolic.language.pos.PartOfSpeechTag
import org.clulab.processors.clu.CluProcessor
import org.clulab.processors.corenlp.CoreNLPProcessor
import org.clulab.processors.fastnlp.FastNLPProcessor
import org.clulab.processors.shallownlp.ShallowNLPProcessor
import org.clulab.processors.{Document, Processor}
import org.clulab.struct.DirectedGraphEdgeIterator

import scala.collection.mutable

class LanguageProcessor {

  // any processor works here! Try FastNLPProcessor, or our own CluProcessor
  private val proc:Processor = new CoreNLPProcessor()
  //  private val proc:Processor = new FastNLPProcessor()

  //  org.clulab.dynet.Utils.initializeDyNet()
  //  private val proc:Processor = new CluProcessor()


  def process(statement: String): Unit = {
    import i4s.symbolic.language._

    // create the processor

    // the actual work is done here
    val doc = proc.annotate(statement)

    // you are basically done. the rest of this code simply prints out the annotations
    // let's print the sentence-level annotations
    var sentenceCount = 0
    for (sentence <- doc.sentences) {
      val tokenMap = mutable.HashMap[String,Token]()

      val tokenData = (sentence.words.toList ::
       sentence.tags.getOrElse(Array.fill(sentence.words.length)("")).toList ::
       sentence.lemmas.getOrElse(Array.fill(sentence.words.length)("")).toList ::
       sentence.chunks.getOrElse(Array.fill(sentence.words.length)("")).toList ::
       sentence.entities.getOrElse(Array.fill(sentence.words.length)("")).toList ::
       sentence.norms.getOrElse(Array.fill(sentence.words.length)("")).toList ::
       Nil).transpose

      println(tokenData.head)
      tokenData.foreach {
        case word :: tag :: lemma :: chunk :: entity :: norm :: Nil =>
          PartOfSpeechTag.withNameOption(tag).foreach { pos =>
            tokenMap += (word -> Token(pos,word,lemma,chunk,entity,norm))
          }
      }

      println("Sentence #" + sentenceCount + ":")
      println("Tokens: " + sentence.words.mkString(" "))
      println("Start character offsets: " + sentence.startOffsets.mkString(" "))
      println("End character offsets: " + sentence.endOffsets.mkString(" "))

      // these annotations are optional, so they are stored using Option objects, hence the foreach statement
      sentence.lemmas.foreach(lemmas => println(s"Lemmas: ${lemmas.mkString(" ")}"))
      sentence.tags.foreach(tags => println(s"POS tags: ${tags.mkString(" ")}"))
      sentence.chunks.foreach(chunks => println(s"Chunks: ${chunks.mkString(" ")}"))
      sentence.entities.foreach(entities => println(s"Named entities: ${entities.mkString(" ")}"))
      sentence.norms.foreach(norms => println(s"Normalized entities: ${norms.mkString(" ")}"))

      println(s"Dependency graphs: ${sentence.graphs}")

      sentence.dependencies.foreach(dependencies => {
        println("Syntactic dependencies:")
        val iterator = new DirectedGraphEdgeIterator[String](dependencies)
        while(iterator.hasNext) {
          val dep = iterator.next
          // note that we use offsets starting at 0 (unlike CoreNLP, which uses offsets starting at 1)
          println(" head:" + dep._1 + " modifier:" + dep._2 + " label:" + dep._3)
        }
      })
      sentence.syntacticTree.foreach(tree => {
        println("Constituent tree: " + tree)
        // see the org.clulab.utils.Tree class for more information
        // on syntactic trees, including access to head phrases/words

        println(tree.describe())
        tree.grammar(tokenMap.toMap).foreach(println)
      })

      println(s"OpenIE relations: ${sentence.relations}")

      sentenceCount += 1
      println("\n")
    }

    // let's print the coreference chains
    doc.coreferenceChains.foreach(chains => {
      for (chain <- chains.getChains) {
        println("Found one coreference chain containing the following mentions:")
        for (mention <- chain) {
          // note that all these offsets start at 0 too
          println("\tsentenceIndex:" + mention.sentenceIndex +
            " headIndex:" + mention.headIndex +
            " startTokenOffset:" + mention.startOffset +
            " endTokenOffset:" + mention.endOffset +
            " text: " + doc.sentences(mention.sentenceIndex).words.slice(mention.startOffset, mention.endOffset).mkString("[", " ", "]"))
        }
      }
    })
  }
}
