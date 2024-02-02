import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.textract.TextractClient;
import software.amazon.awssdk.services.textract.model.Document;
import software.amazon.awssdk.services.textract.model.DetectDocumentTextRequest;
import software.amazon.awssdk.services.textract.model.DetectDocumentTextResponse;
import software.amazon.awssdk.services.textract.model.Block;
import software.amazon.awssdk.services.textract.model.DocumentMetadata;
import software.amazon.awssdk.services.textract.model.TextractException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import scala.collection.JavaConverters._
import smithy4s.aws.*

/**
 * Before running this Java V2 code example, set up your development
 * environment, including your credentials.
 *
 * For more information, see the following documentation topic:
 *
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */
@main def textract =
    val sourceDoc = "C:\\temp\\B.png";
    val region = Region.EU_WEST_1;
    val textractClient = TextractClient.builder()
            .region(region)
            .build();

    detectDocText(textractClient, sourceDoc);
    textractClient.close();


def detectDocText(textractClient: TextractClient, sourceDoc: String)  = {
    val sourceStream = new FileInputStream(new File(sourceDoc));
    val sourceBytes = SdkBytes.fromInputStream(sourceStream);

    // Get the input Document object as bytes.
    val myDoc = Document.builder()
            .bytes(sourceBytes)
            .build();

    val detectDocumentTextRequest = DetectDocumentTextRequest.builder()
            .document(myDoc)
            .build();

    // Invoke the Detect operation.
    val textResponse = textractClient.detectDocumentText(detectDocumentTextRequest);
    val docInfo = textResponse.blocks().asScala;
    for ( block <- docInfo) {
        println("The block type is " + block.blockType().toString());
        println(block)
    }

    val documentMetadata = textResponse.documentMetadata();
    println("The number of pages in the document is " + documentMetadata.pages());
    println("The number of pages in the document is " + documentMetadata. );

  }
